package gov.nih.nci.cagrid.introduce.upgrade;

import gov.nih.nci.cagrid.introduce.IntroduceConstants;
import gov.nih.nci.cagrid.introduce.codegen.SyncTools;
import gov.nih.nci.cagrid.introduce.common.IntroducePropertiesManager;
import gov.nih.nci.cagrid.introduce.common.ResourceManager;
import gov.nih.nci.cagrid.introduce.common.ServiceInformation;
import gov.nih.nci.cagrid.introduce.upgrade.common.IntroduceUpgradeStatus;
import gov.nih.nci.cagrid.introduce.upgrade.common.UpgradeStatus;
import gov.nih.nci.cagrid.introduce.upgrade.common.UpgradeUtilities;

import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.Logger;


public class UpgradeManager {

    private IntroduceUpgradeManager iUpgrader;

    private String pathToService;
    private String id;
    private static final Logger logger = Logger.getLogger(UpgradeManager.class);


    public UpgradeManager(String pathToService) {
        this.pathToService = pathToService;
        iUpgrader = new IntroduceUpgradeManager(pathToService);
    }


    public boolean canIntroduceBeUpgraded() {
        boolean canUpgrade = false;
        try {
            canUpgrade = iUpgrader.needsUpgrading()
                && iUpgrader.canBeUpgraded(UpgradeUtilities.getCurrentServiceVersion(
                    pathToService + File.separator + IntroduceConstants.INTRODUCE_XML_FILE));
        } catch (Exception e) {
            logger.error(e);
        }
        return canUpgrade;
    }


    public boolean introduceNeedsUpgraded() {
        return iUpgrader.needsUpgrading();
    }


    public boolean extensionsNeedUpgraded() {
        boolean needsUpgrade = false;
        if (!canIntroduceBeUpgraded()) {
            ServiceInformation info = null;
            try {
                info = new ServiceInformation(new File(pathToService));
            } catch (Exception e) {
                logger.error(e);
            }
            ExtensionsUpgradeManager eUpgradeManager = new ExtensionsUpgradeManager(info, pathToService);
            needsUpgrade = eUpgradeManager.needsUpgrading();
        }
        return needsUpgrade;
    }


    private void backup() throws Exception {
        logger.info("Creating backup of service prior to upgrading.");
        id = String.valueOf(System.currentTimeMillis());
        ResourceManager.createArchive(id, getUpgradeServiceName(), pathToService);
    }


    public void recover() throws Exception {
        logger.info("Recovering backup of service after failed upgrade.");
        ResourceManager.restoreSpecific(id, getUpgradeServiceName(), pathToService);
    }


    private String getUpgradeServiceName() throws Exception {
        String upgradeServiceName = UpgradeUtilities.getServiceName(
            pathToService + File.separator + IntroduceConstants.INTRODUCE_XML_FILE)
            + "UPGRADE";
        return upgradeServiceName;
    }


    public UpgradeStatus upgrade() throws Exception {
        UpgradeStatus status = new UpgradeStatus();
        backup();

        if (canIntroduceBeUpgraded()) {
            upgradeIntroduce(status);
            try {
                SyncTools sync = new SyncTools(new File(this.pathToService));
                sync.sync();
            } catch (Exception e) {
                String message = 
                    "Re-Sync Failed: " + e.getMessage() + "\n"
                     + "This could be due to modifications you may have made to Introduce\n"
                     + "managed files such as the build files, source files or wsdl files.\n"
                     + "Once the build is fixed then a sync must be done to "
                     + "complete the upgrade.  To complete the upgrade simply "
                     + "open introduce and open this service for modification " 
                     + "and then click save.";
                status.addIssue("Re-Sync Failed", message);
                e.printStackTrace();
                logger.error(message);
            }
        } else if (extensionsNeedUpgraded()) {
            status.addIntroduceUpgradeStatus(upgradeExtensionsOnly());
            try {
                SyncTools sync = new SyncTools(new File(this.pathToService));
                sync.sync();
            } catch (Exception e) {
                String message = "Re-Sync Failed: " + e.getMessage() + "\n"
                    + "This could be due to modifications you may have made to Introduce\n"
                    + "managed files such as the build files, source files or wsdl files.\n"
                    + "Once the build is fixed then a sync must be done to "
                    + "complete the upgrade. To complete the upgrade simply open introduce and open "
                    + "this service for modification and then click save.";
                status.addIssue("Re-Sync Failed", message);
                e.printStackTrace();
                logger.error(message, e);
            }
        }

        // send the status to a log file
        try {
            File upgradeLog = new File(pathToService, "introduce-upgrade-"
                + IntroducePropertiesManager.getIntroduceVersion() + ".log");
            FileWriter writer = new FileWriter(upgradeLog);
            writer.write(status.toString());
            writer.close();
        } catch (Exception ex) {
            logger.warn("Error writing upgrade log to file: " + ex.getMessage(), ex);
        }

        return status;
    }


    private void upgradeIntroduce(UpgradeStatus status) throws Exception {
        if (iUpgrader.needsUpgrading()) {
            try {
                iUpgrader.upgrade(status);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Service upgrader failed: " + e.getMessage(), e);
            }
        }
    }


    private IntroduceUpgradeStatus upgradeExtensionsOnly() throws Exception {
        IntroduceUpgradeStatus status = null;
        if (!iUpgrader.needsUpgrading()) {
            try {
                ServiceInformation info = null;
                try {
                    info = new ServiceInformation(new File(pathToService));
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e);
                }
                status = new IntroduceUpgradeStatus();
                ExtensionsUpgradeManager eUpgradeManager = new ExtensionsUpgradeManager(info, pathToService);
                eUpgradeManager.upgrade(status);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Extensions upgrader failed: " + e.getMessage(), e);
            }
        }
        return status;
    }
}
