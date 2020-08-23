package crise.studio.model.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

@Component
public class Enums {

    private static final Logger logger = LoggerFactory.getLogger(Enums.class);

    private static String fileServerPath;
    private static String fileStoragePath;
    private static String awsBucketName;

   // @Value("${config.fileServerPath}")
    public void setFileServerPath(String fileServerPath) {
        logger.info("## Enums :: setFileServerPath :: {}", fileServerPath);
        Enums.fileServerPath = fileServerPath;
    }

   // @Value("${config.fileStoragePath}")
    public void setFileStoragePath(String fileStoragePath) {
        logger.info("## Enums :: setFileStoragePath :: {}", fileStoragePath);
        Enums.fileStoragePath = fileStoragePath;
    }

    //@Value("${config.awsBucketName}")
    public void setAwsBucketName(String awsBucketName) {
        logger.info("## Enums :: awsBucketName :: {}", awsBucketName);
        Enums.awsBucketName = awsBucketName;
    }

    public enum EnviorPath {

        FILE_STORAGE_PATH(fileStoragePath),
        AWS_BUCKET_NAME(awsBucketName),
        FILE_SERVER_PATH(fileServerPath);

        EnviorPath(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("EnviorPath Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }

    }

    public enum Platform {
        ALL("ALL"),
        ACAP("ACAP"),
        WEB("WEB");

        Platform(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("Platform Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }

        public static PlatformCode valueOf(Platform platform) {
            PlatformCode platformCode = null;

            if (Objects.nonNull(platform) && Objects.nonNull(platform.name())) {
                for (PlatformCode code : PlatformCode.values()) {
                    if (platform.name().equals(code.getLongVal())) {
                        platformCode = code;
                        break;
                    }
                }
            }

            return platformCode;
        }
    }

    public enum PlatformCode {
        ACAP("A", "ACAP"),
        WEB("W", "WEB");

        private String shortVal;
        private String longVal;

        PlatformCode(String shortVal, String longVal) {

            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, shortVal);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("PlatformCode Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }

            this.shortVal = shortVal;
            this.longVal = longVal;
        }

        public String getShortVal() {
            return shortVal;
        }

        public String getLongVal() {
            return longVal;
        }
    }

    public enum UserGroup {
        MONTHLY("MONTHLY", "월간 결제 회원"),
        CONTRACT("CONTRACT", "약정 결제 회원"),
        FOG("FOG", "전화 가입 회원"),
        FREE("FREE", "휴면 회원");

        private String shortVal;
        private String longVal;

        UserGroup(String shortVal, String longVal) {

            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, shortVal);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("UserGroup Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }

            this.shortVal = shortVal;
            this.longVal = longVal;
        }

        public String getShortVal() {
            return shortVal;
        }

        public String getLongVal() {
            return longVal;
        }

    }

    public enum StickerType {
        NORMAL("NORMAL", "일반"),
        NEW("NEW", "신규"),
        FREE("FREE", "무료"),
        HOT("HOT", "인기");

        private String shortVal;
        private String longVal;

        StickerType(String shortVal, String longVal) {

            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, shortVal);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("StickerType Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }

            this.shortVal = shortVal;
            this.longVal = longVal;
        }

        public String getShortVal() {
            return shortVal;
        }

        public String getLongVal() {
            return longVal;
        }
    }

    public enum ConfigurationCode {
        AUTO_REPEAT_VOD_PLAY("ARVP"),
        DLS("DLS"),
        BACKGROUND_DISPLAY_LANDING("BGD"),
        BACKGROUND_DISPLAY_CATEGORY("CBGD"),
        INTERVAL_SECONDS_DISPLAY("ISD"),
        REGISTER_INFORMATION_DISPLAY("RID"),
        BACKGROUND_MUSIC("BGM"),
        KARAOKE_FEEDBACK_DISPLAY("KFD"),
        LANDING_FOCUS_1("LF1"),//랜딩 포커스 1
        LANDING_FOCUS_2("LF2"),//랜딩 포커스 2
        CATEGORY_FOCUS("CF"),//카테고리 포커스
        SERVICE_ID("SID");

        ConfigurationCode(String value) {

            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("Configuration Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }
    }

    public enum ReferenceTable {

        MESSAGE("tl_message"),
        META("tl_meta"),
        CONFIGURATION("tl_configuration"),
        ENTITY("tl_entity"),
        INTERACTION("tl_interaction"),
        STB("tl_stb_model"),
        ADMIN_GROUP("tl_admin_group"),
        ADMIN("tl_admin"),
        ADMIN_MENU("tl_admin_menu"),
        PRODUCT("tl_payment_product"),
        LANDING_FORMATION("tl_landing_formation")
        ;

        ReferenceTable(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("File ReferenceTable Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }
    }

    public enum FileUseCase {

        DEFAULT("DEF"),
        ENTITY_THUMBNAIL_NORMAL("ETN"),
        ENTITY_THUMBNAIL_BIG("ETB"),
        META_THUMBNAIL("MT"),
        META_CATEGORY_PLAY_ALL_IMG("MCPI"),
        
        META_CATEGORY_WEB_BACKGROUND_IMG("MCWBI"),// 카테고리 WEB 배경이미지 
        META_CATEGORY_ACAP_BACKGROUND_IMG("MCABI"),// 카테고리 ACAP 배경이미지 
        META_CATEGORY_BACKGROUND_MUSIC("MCBM"), // 카테고리 배경음악 

        LANDING_FORMATION_WEB_BACKGROUND_IMG("LFWBI"),// 랜딩페이지 WEB 배경이미지
        LANDING_FORMATION_ACAP_BACKGROUND_IMG("LFABI"),// 랜딩페이지 ACAP 배경이미지
        LANDING_FORMATION_ACAP_BACKGROUND_MUSIC("LFABM"),//랜딩페이지 ACAP 배경음악
        LANDING_FORMATION_WEB_BACKGROUND_MUSIC("LFWBM"),//랜딩페이지 WEB 배경음악

        LANDING_FOCUS1("LF1"), //랜딩 포커스1
        LANDING_FOCUS2("LF2"),//랜딩 포커스2
        CATEGORY_FOCUS("CF"),//카테고리 포커스

        META_USER_GUIDE_IMG("MUGI"),
        META_NOTICE_IMG("MNI"),
        META_EVENT_AGREE_GATHER_IMG("MEAGI"), // 정보 수집
        META_EVENT_AGREE_ENTRUST_IMG("MEAEI"), // 정보 위탁
        META_EVENT_AGREE_SUPPLY_IMG("MEASI"), // 제 3자 정보 제공
        META_EVENT_GUIDE_IMG("MEGI"),
        META_GALLERY_CONTENT("MGC"),
        INTERACTION("IA"),
        CONFIG_BGD_IMG("BGD"),
        CONFIG_CBGD_IMG("CBGD"),
        CONFIG_DLS_IMG("DLS"),
        CONFIG_ISD_IMG("ISD"),
        CONFIG_KFD_IMG("KFD"),
        CONFIG_RID_IMG("RID"),
        CONFIG_BACKGROUND_MUSIC("BGM");

        FileUseCase(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("File UseCase Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }
    }

    public enum FunctionTarget {

        ENTITY("ENTITY"),
        META("META");

        FunctionTarget(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("FunctionTarget Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }

    }

    public enum FunctionCode {

        FAVORVODS("FAVORVODS"),
        CATEGORY("CAT"),
        VOD("VOD"),
        TVA("TVA"),
        EVENT("EVENT"),
        GALLERY("GALLERY"),
        GUIDE("GUIDE"),
        NOTICE("NOTICE"),
        NOTICELIST("NOTICELIST"),
        ACTIVATE("ACTIVATE");

        FunctionCode(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("FunctionCode Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }

    }

    public enum Resource {
        EVENT("이벤트"),
        NOTICE_PAGE("공지사항 페이지"),
        FAVORITE_VOD("인기영상"),
        CATEGORY("카테고리"),
        CATEGORY_PAGE("카테고리 페이지"),
        META("메타"),
        LANDING_PAGE("랜딩 페이지"),
        USER_GROUP("회원 그룹"),
        VOD_PLAY_LOG("VOD 재생이력"),
        VOD("VOD"),
        LIFECYCLE("TVA실행 이력"),
        STB_USER("회원");

        Resource(String value) {

            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("Resource Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }
    }

    public enum EventType {
        MEMBERSHIP("MEMBERSHIP"),
        ATTRACT("ATTRACT");

        EventType(String value) {

            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("EventType Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }
    }

    public enum FormationUpdateHistoryType {

        INSERT("INSERT"),
        UPDATE("UPDATE"),
        DELETE("DELETE"),
        RESTORE("RESTORE");

        FormationUpdateHistoryType(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("FormationUpdateHistoryType Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }

        public static FormationUpdateHistoryType getInsertOrUpdateTypeByPK(Integer pk) {
            return Objects.isNull(pk) ? INSERT : UPDATE;
        }

        public static FormationUpdateHistoryType getInsertOrUpdateTypeByPK(Long pk) {
            return getInsertOrUpdateTypeByPK(Objects.isNull(pk) ? null : pk.intValue());
        }

    }

    public enum CategoryStaticType {
        HOTVOD
    }

    public enum Period {
        DAILY("DAILY"),
        WEEKLY("WEEKLY"),
        MONTHLY("MONTHLY");

        Period(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("Period Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }
    }

    public enum ESLogType {

        ERROR("error"),
        META("meta"),
        LANDING_PAGE("landingPage"),
        FIRST_ACCESS("firstAccess"),
        VOD_PLAY("vodPlay");

        ESLogType(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("FunctionCode Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }
    }

    public enum StatisticsType {
        UV("UV"),
        PV("PV");

        StatisticsType(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("StatisticsType Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }
    }

    public enum ServerType {
        LOCAL("LOCAL"),
        STAGING("STAGING"),
        DEVELOPMENT("DEVELOPMENT"),
        PRODUCTION("PRODUCTION");

        ServerType(String value) {
            try {
                Field fieldName = getClass().getSuperclass().getDeclaredField("name");
                fieldName.setAccessible(true);
                fieldName.set(this, value);
                fieldName.setAccessible(false);
            } catch (Exception e) {
                logger.warn("ServerType Enum Aries Exception - Name : {}, Msg : {}", e.getClass().getName(), e.getMessage());
            }
        }
    }

    public enum BackOfficeMenuLevel {
        PRIME, SUB
    }
}
