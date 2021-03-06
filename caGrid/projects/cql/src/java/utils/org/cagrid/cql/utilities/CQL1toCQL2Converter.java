/**
*============================================================================
*  Copyright The Ohio State University Research Foundation, The University of Chicago - 
*	Argonne National Laboratory, Emory University, SemanticBits LLC, and 
*	Ekagra Software Technologies Ltd.
*
*  Distributed under the OSI-approved BSD 3-Clause License.
*  See http://ncip.github.com/cagrid-core/LICENSE.txt for details.
*============================================================================
**/
package org.cagrid.cql.utilities;

import gov.nih.nci.cagrid.cqlquery.Association;
import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.Group;
import gov.nih.nci.cagrid.cqlquery.LogicalOperator;
import gov.nih.nci.cagrid.cqlquery.Object;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.cagrid.cqlquery.QueryModifier;
import gov.nih.nci.cagrid.metadata.common.UMLAttribute;
import gov.nih.nci.cagrid.metadata.common.UMLClass;
import gov.nih.nci.cagrid.metadata.common.UMLClassUmlAttributeCollection;
import gov.nih.nci.cagrid.metadata.dataservice.DomainModel;
import gov.nih.nci.cagrid.metadata.dataservice.UMLClassReference;
import gov.nih.nci.cagrid.metadata.dataservice.UMLGeneralization;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cagrid.cql2.Aggregation;
import org.cagrid.cql2.AttributeValue;
import org.cagrid.cql2.BinaryPredicate;
import org.cagrid.cql2.CQLAssociatedObject;
import org.cagrid.cql2.CQLAttribute;
import org.cagrid.cql2.CQLGroup;
import org.cagrid.cql2.CQLObject;
import org.cagrid.cql2.CQLQuery;
import org.cagrid.cql2.CQLQueryModifier;
import org.cagrid.cql2.CQLTargetObject;
import org.cagrid.cql2.DistinctAttribute;
import org.cagrid.cql2.GroupLogicalOperator;
import org.cagrid.cql2.NamedAttribute;
import org.cagrid.cql2.UnaryPredicate;

public class CQL1toCQL2Converter {
    
    private static Map<Predicate, BinaryPredicate> binaryPredicateConversion = null;
    private static Map<Predicate, UnaryPredicate> unaryPredicateConversion = null;
    static {
        binaryPredicateConversion = new HashMap<Predicate, BinaryPredicate>();
        unaryPredicateConversion = new HashMap<Predicate, UnaryPredicate>();
        binaryPredicateConversion.put(null, BinaryPredicate.EQUAL_TO); // default to EQUAL_TO
        binaryPredicateConversion.put(Predicate.EQUAL_TO, BinaryPredicate.EQUAL_TO);
        binaryPredicateConversion.put(Predicate.GREATER_THAN, BinaryPredicate.GREATER_THAN);
        binaryPredicateConversion.put(Predicate.GREATER_THAN_EQUAL_TO, BinaryPredicate.GREATER_THAN_EQUAL_TO);
        binaryPredicateConversion.put(Predicate.LESS_THAN, BinaryPredicate.LESS_THAN);
        binaryPredicateConversion.put(Predicate.LESS_THAN_EQUAL_TO, BinaryPredicate.LESS_THAN_EQUAL_TO);
        binaryPredicateConversion.put(Predicate.LIKE, BinaryPredicate.LIKE);
        binaryPredicateConversion.put(Predicate.NOT_EQUAL_TO, BinaryPredicate.NOT_EQUAL_TO);
        unaryPredicateConversion.put(Predicate.IS_NOT_NULL, UnaryPredicate.IS_NOT_NULL);
        unaryPredicateConversion.put(Predicate.IS_NULL, UnaryPredicate.IS_NULL);
    }
    
    
    private DomainModel model = null;
    private RoleNameResolver resolver = null;
    
    public CQL1toCQL2Converter(DomainModel model) {
        this.model = model;
        this.resolver = new RoleNameResolver(model);
    }
    

    public CQLQuery convertToCql2Query(gov.nih.nci.cagrid.cqlquery.CQLQuery cqlQuery) throws QueryConversionException {
        CQLQuery cql2 = new CQLQuery();
        // walk the core of the object model
        CQLTargetObject target = convertTarget(cqlQuery.getTarget());
        cql2.setCQLTargetObject(target);
        // convert any modifiers
        if (cqlQuery.getQueryModifier() != null) {
            cql2.setCQLQueryModifier(convertQueryModifier(cqlQuery.getQueryModifier()));
        }
        return cql2;
    }
    
    
    private CQLTargetObject convertTarget(Object cqlTarget) throws QueryConversionException {
        CQLTargetObject target = new CQLTargetObject();
        convertObject(cqlTarget, target);
        return target;
    }
    
    
    private CQLAssociatedObject convertAssociation(String parentClassName, Association cqlAssoc) throws QueryConversionException {
        CQLAssociatedObject assoc = new CQLAssociatedObject();
        convertObject(cqlAssoc, assoc);
        assoc.setClassName(cqlAssoc.getName());
        String roleName = null;
        try {
            roleName = resolver.getRoleName(parentClassName, cqlAssoc);
        } catch (RoleNameResolutionException ex) {
            throw new QueryConversionException("Error resolving role name: " + ex.getMessage(), ex);
        }
        assoc.setEndName(roleName);
        return assoc;
    }
    
    
    private void convertObject(Object cqlObj, CQLObject instance) throws QueryConversionException {
        instance.setClassName(cqlObj.getName());
        if (cqlObj.getAttribute() != null) {
            CQLAttribute convertedAttribute = null;
            // determine if the attribute is unary or binary in CQL 2
            if (unaryPredicateConversion.containsKey(cqlObj.getAttribute().getPredicate())) {
                convertedAttribute = convertUnaryAttribute(cqlObj.getAttribute());
            } else {
                convertedAttribute = convertBinaryAttribute(cqlObj.getName(), cqlObj.getAttribute());
            }
            instance.setCQLAttribute(convertedAttribute);
        } else if (cqlObj.getAssociation() != null) {
            instance.setCQLAssociatedObject(convertAssociation(cqlObj.getName(), cqlObj.getAssociation()));
        } else if (cqlObj.getGroup() != null) {
            instance.setCQLGroup(convertGroup(cqlObj.getName(), cqlObj.getGroup()));
        }
    }
    
    
    private CQLGroup convertGroup(String parentClassName, Group cqlGroup) throws QueryConversionException {
        CQLGroup group = new CQLGroup();
        if (cqlGroup.getAssociation() != null) {
            CQLAssociatedObject[] associations = new CQLAssociatedObject[cqlGroup.getAssociation().length];
            for (int i = 0; i < cqlGroup.getAssociation().length; i++) {
                associations[i] = convertAssociation(parentClassName, cqlGroup.getAssociation(i));
            }
            group.setCQLAssociatedObject(associations);
        }
        if (cqlGroup.getAttribute() != null) {
            List<CQLAttribute> convertedAttributes = new LinkedList<CQLAttribute>();
            for (Attribute attrib : cqlGroup.getAttribute()) {
                CQLAttribute conversion = null;
                if (unaryPredicateConversion.containsKey(attrib.getPredicate())) {
                    conversion = convertUnaryAttribute(attrib);
                } else {
                    conversion = convertBinaryAttribute(parentClassName, attrib);
                }
                convertedAttributes.add(conversion);
            }
            if (convertedAttributes.size() != 0) {
                CQLAttribute[] conversions = new CQLAttribute[convertedAttributes.size()];
                convertedAttributes.toArray(conversions);
                group.setCQLAttribute(conversions);
            }
        }
        if (cqlGroup.getGroup() != null) {
            CQLGroup[] groups = new CQLGroup[cqlGroup.getGroup().length];
            for (int i = 0; i < cqlGroup.getGroup().length; i++) {
                groups[i] = convertGroup(parentClassName, cqlGroup.getGroup(i));
            }
            group.setCQLGroup(groups);
        }
        GroupLogicalOperator logic = 
            cqlGroup.getLogicRelation() == LogicalOperator.AND 
                ? GroupLogicalOperator.AND : GroupLogicalOperator.OR;
        group.setLogicalOperation(logic);
        return group;
    }
    
    
    private CQLAttribute convertBinaryAttribute(String className, Attribute cqlAttribute) throws QueryConversionException {
        CQLAttribute bin = new CQLAttribute();
        bin.setName(cqlAttribute.getName());
        BinaryPredicate predicate = binaryPredicateConversion.get(cqlAttribute.getPredicate());
        bin.setBinaryPredicate(predicate);
        AttributeValue value = convertAttributeValue(
            className, cqlAttribute.getName(), cqlAttribute.getValue());
        bin.setAttributeValue(value);
        return bin;
    }
    
    
    private CQLAttribute convertUnaryAttribute(Attribute cqlAttribute) {
        CQLAttribute un = new CQLAttribute();
        un.setName(cqlAttribute.getName());
        un.setUnaryPredicate(unaryPredicateConversion.get(cqlAttribute.getPredicate()));
        return un;
    }
    
    
    private AttributeValue convertAttributeValue(String className, String attributeName, String rawValue) throws QueryConversionException {
        String datatypeName = null;
        UMLClass[] classes = model.getExposedUMLClassCollection().getUMLClass();
        for (UMLClass c : classes) {
            String fqName = c.getClassName();
            if (c.getPackageName() != null && c.getPackageName().length() != 0) {
                fqName = c.getPackageName() + "." + c.getClassName();
            }
            if (className.equals(fqName)) {
                List<UMLAttribute> attribs = getAttributesThroughHierarchy(c);
                for (UMLAttribute att : attribs) {
                    if (attributeName.equals(att.getName())) {
                        datatypeName = att.getDataTypeName();
                        break;
                    }
                }
            }
        }
        AttributeValue val = new AttributeValue();
        if (datatypeName != null) {
            if (String.class.getName().equals(datatypeName)) {
                val.setStringValue(rawValue);
            } else if (Boolean.class.getName().equals(datatypeName)) {
                val.setBooleanValue(Boolean.valueOf(rawValue));
            } else if (Date.class.getName().equals(datatypeName)) {
                Date date = null;
                try {
                    date = DateFormat.getDateInstance().parse(rawValue);
                } catch (ParseException ex) {
                    throw new QueryConversionException(
                        "Error converting value " + rawValue + " to date: " + ex.getMessage(), ex);
                }
                val.setDateValue(date);
            } else if (Integer.class.getName().equals(datatypeName)) {
                try {
                    val.setIntegerValue(Integer.valueOf(rawValue));
                } catch (NumberFormatException ex) {
                    throw new QueryConversionException(
                        "Error converting value " + rawValue + " to integer: " + ex.getMessage(), ex);
                }
            } else if (Long.class.getName().equals(datatypeName)) {
                try {
                    val.setLongValue(Long.valueOf(rawValue));
                } catch (NumberFormatException ex) {
                    throw new QueryConversionException(
                        "Error converting value " + rawValue + " to long: " + ex.getMessage(), ex);
                }
            } else if (Double.class.getName().equals(datatypeName)) {
                try {
                    val.setDoubleValue(Double.valueOf(rawValue));
                } catch (NumberFormatException ex) {
                    throw new QueryConversionException(
                        "Error converting value " + rawValue + " to double: " + ex.getMessage(), ex);
                }
            }
        } else {
            throw new QueryConversionException("No data type could be determined for attribute " 
                + attributeName + " of class " + className);
        }
        return val;
    }
    
    
    private CQLQueryModifier convertQueryModifier(QueryModifier cqlModifier) {
        CQLQueryModifier mods = new CQLQueryModifier();
        if (cqlModifier.isCountOnly()) {
            mods.setCountOnly(Boolean.valueOf(cqlModifier.isCountOnly()));
        }
        if (cqlModifier.getDistinctAttribute() != null) {
            DistinctAttribute attrib = new DistinctAttribute();
            attrib.setAttributeName(cqlModifier.getDistinctAttribute());
            if (cqlModifier.isCountOnly()) {
                attrib.setAggregation(Aggregation.COUNT);
            }
            mods.setDistinctAttribute(attrib);
        } else if (cqlModifier.getAttributeNames() != null) {
            NamedAttribute[] named = new NamedAttribute[cqlModifier.getAttributeNames().length];
            for (int i = 0; i < cqlModifier.getAttributeNames().length; i++) {
                named[i] = new NamedAttribute();
                named[i].setAttributeName(cqlModifier.getAttributeNames(i));
            }
            mods.setNamedAttribute(named);
        }
        return mods;
    }
    
    
    private List<UMLAttribute> getAttributesThroughHierarchy(UMLClass clazz) {
        List<UMLClass> classHierarchy = getSuperclasses(
            clazz.getPackageName() + "." + clazz.getClassName());
        classHierarchy.add(0, clazz);
        List<UMLAttribute> attribs = new ArrayList<UMLAttribute>();
        for (UMLClass c : classHierarchy) {
            UMLClassUmlAttributeCollection attribCollection = c.getUmlAttributeCollection();
            if (attribCollection != null && attribCollection.getUMLAttribute() != null) {
                Collections.addAll(attribs, attribCollection.getUMLAttribute());
            }
        }
        return attribs;
    }
    
    
    private List<UMLClass> getSuperclasses(String className) {
        UMLGeneralization[] generalizations = model.getUmlGeneralizationCollection().getUMLGeneralization();
        List<UMLClass> superclasses = new ArrayList<UMLClass>();
        // find all generalizations where subclass is the class in question,
        // then get the superclasses from each
        for (int i = 0; generalizations != null && i < generalizations.length; i++) {
            UMLClassReference subClassRef = generalizations[i].getSubClassReference();
            UMLClassReference superClassRef = generalizations[i].getSuperClassReference();
            if (subClassRef != null && superClassRef != null) {
                UMLClass subclass = getReferencedUMLClass(subClassRef.getRefid());
                if ((subclass.getPackageName() + "." + subclass.getClassName()).equals(className)) {
                    UMLClass superclass = getReferencedUMLClass(superClassRef.getRefid());
                    superclasses.add(superclass);
                    // get superclasses of the superclass
                    superclasses.addAll(getSuperclasses(superclass.getPackageName() + "."
                        + superclass.getClassName()));
                }
            }
        }
        return superclasses;
    }
    
    
    private UMLClass getReferencedUMLClass(String refid) {
        UMLClass c = null;
        for (UMLClass clazz : model.getExposedUMLClassCollection().getUMLClass()) {
            if (refid.equals(clazz.getId())) {
                c = clazz;
                break;
            }
        }
        return c;
    }
}
