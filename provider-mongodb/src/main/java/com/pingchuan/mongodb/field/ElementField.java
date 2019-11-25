package com.pingchuan.mongodb.field;

import org.springframework.data.mongodb.core.aggregation.Fields;

public class ElementField {

    public static Fields elementInfoFields = Fields.fields("element_info.start_time", "element_info.update_time", "element_info.element_code","element_info.forecast_model", "element_info.trapezoid_info_id");

    public static Fields realInfoFields = Fields.fields("real_info.real_time", "real_info.update_time", "real_info.element_code", "real_info.trapezoid_info_id");

    public static Fields trapezoidFields = Fields.fields("start_time", "update_time", "element_code","forecast_model", "element_info_id", "trapezoid.grid_code", "trapezoid.loc", "trapezoid.area_code", "trapezoid.area_name");

    public static Fields forecastInfoFields = Fields.fields("start_time", "update_time", "element_code","forecast_model", "grid_code", "loc", "area_code", "area_name", "forecast_info.forecast_time", "forecast_info.time_effect");

    public static Fields elementValueFields = Fields.fields("start_time", "update_time", "element_code","forecast_model", "grid_code", "loc", "area_code", "area_name", "forecast_time", "time_effect", "element_value.value");
}
