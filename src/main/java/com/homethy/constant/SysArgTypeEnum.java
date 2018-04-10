package com.homethy.constant;

/**
 * @author lanxu
 * @email xu.lan@renren-inc.com
 * @create 2017年07月10 13:12
 **/
public enum SysArgTypeEnum {
  DEFAULT_NEIGHBORHOOD_BANNER("default_neighborhood_banner", Constants.DEFAULT_SPLIT),
  LOGO_HEIGHT("logo_height"),
  SHOW_PROVIDED("show_provided"),
  HOME_EVALUATION_OWNER_EMAIL("home_evaluation_owner_emails", Constants.DEFAULT_SPLIT),
  HOME_EVALUATION_GOOGLE_KEY("Google_Map_Key"),
  CHAT_CONTENT("chat_content"),
  CUSTOMIZE_MENU("customize_menu"),
  SEO_CITY("seo_city"),
  SITE_THEME_COLOR_DEFAULT("site_theme_color_default"),
  SITE_THEME_COLOR_CLICK("site_theme_color_click"),
  SITE_LISTING_RECOMMEND("site_listing_recommend"),
  MLS_LISTING_STORY("mls_listing_story"),
  BANNER_ALT_TAG("BANNER_ALT_TAG"),
  LISTING_EXCLUDE_OPEN_HOUSE("LISTING_EXCLUDE_OPEN_HOUSE"),

  SOLD_LISTING_BID_CONFIG("sold_listing_bid_config"),
  SOLD_LISTING_PRICE_MIN("sold_listing_price_min"),
  SOLD_LISTING_PRICE_MARK("sold_listing_price_mark"),
  MAP_GRID_SIZE("map_grid_size"),
  MAP_MIN_AGG_ZOOM("map_min_agg_zoom"),
  ZILLOW_CLIENT_ID("ZILLOW_CLIENT_ID"),
  MAP_MIN_AGG_COUNT("map_min_agg_count"),
  CHAT_BOX_SHOW("CHAT_BOX_SHOW"),
  WORDPRESS_BLOG_OFF("WORDPRESS_BLOG_OFF"),
  NEIGHBORHOOD_MLS_DATA_ON("NEIGHBORHOOD_MLS_DATA_ON"),
  IS_NEIGHBORHOOD_V3("is_neighborhood_v3"),
  SHOW_ELEMENTARY_SCHOOL_SEARCH("show_elementary_school_search"),
  LISTING_NEARBY_DISTANCE("LISTING_NEARBY_DISTANCE"),
  EXCUTE_TASKS_KEY("EXCUTE_TASKS_KEY"),
  SOLD_LISTING_MAP_MIN_AGG_COUNT("sold_listing_map_min_agg_count"),
  SOLD_LISTING_MAP_MIN_AGG_ZOOM("sold_listing_map_min_agg_zoom"),
  SOLD_LISTING_MAP_GRID_SIZE("sold_listing_map_grid_size"),
  LISTING_COUNT("listing_count"),
  SOLD_LISTING_COUNT("sold_listing_count"),
  HOME_FEATURE_LISTING_COUNT("home_feature_listing_count"),
  NEIGHBORHOOD_DETAIL_LISTING_COUNT("neighborhood_detail_listing_count"),
  NEIGHBORHOOD_CITY_LISTING_COUNT("neighborhood_city_listing_count"),
  IS_NEW_HOME_STYLE("is_new_home_style"),
  ABOUT_STYLE("about_style"),
  SHOW_ADVERTISE_PAGE("show_advertise_page"),
  HIDDEN_SITE_MAP("hidden_site_map"),
  DAYS_ON_SITE("days_on_site"),
  TRACKING_EVALUATION_ACTION_STEPPED("tracking_evaluation_action_stepped"),
  SHOW_SCHOOL_DISTRICT("show_school_district"),
  SOLD_LISTING_MAP_ZOOM("sold_listing_map_zoom"),
  HIDE_MAP_CITY_BOUNDARY("hide_map_city_boundary"),
  CMS_GOLD_PASSWORD("CMS_GOLD_PASSWORD"),

  BANNER_WITH_VIDEO("banner_with_video"),
  IS_AD_EVALUATION("IS_AD_EVALUATION"),
  COMMENT_VIEW_MORE("comment_view_more"),
  SUGGESTION_HIDE_MLS_ORG_IDS("suggestion_hide_mls_org_ids"),
  ALERT_PHONES("alter_phones"),
  SENDER_EMAIL_NAME("sender_email_name"),
  SENDER_EMAIL_PASSWORD("sender_email_password"),
  HIDE_AGENT_NAME("hide_agent_name"),
  SHOW_PREVIOUS_NEXT_LISTING("show_previous_next_listing"),
  SENDER_EMAIL_HOST("sender_email_host");

  private final String split; // [\s;,]+

  private final String type;

  SysArgTypeEnum(String type) {
    this(type, null);
  }

  SysArgTypeEnum(String type, String split) {
    this.type = type;
    this.split = split;
  }

  public String getSplit() {
    return split;
  }

  public String getType() {
    return type;
  }

  public static SysArgTypeEnum getSplitAndMaskByType(String type) {

    for (SysArgTypeEnum sysArgEnum : SysArgTypeEnum.values()) {
      if (sysArgEnum.getType().equals(type)) {
        return sysArgEnum;
      }
    }
    return null;
  }

  private static class Constants {
    public static final String DEFAULT_SPLIT = "[\\s;,]+";

    private Constants() {
    }
  }
}
