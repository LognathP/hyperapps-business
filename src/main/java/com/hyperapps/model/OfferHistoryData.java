package com.hyperapps.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class OfferHistoryData {

    public int id;
    public String store_id;
    public String active;
    public String offer_valid;
    public String offer_start_date;
    public String offer_type;
    public String offer_flat_amount;
    public String offer_percentage;
    public String offer_description;
    public String offer_heading;
    public int offer_applied;
    public int offer_max_apply_count;
    public String offer_percentage_max_amount;
}
