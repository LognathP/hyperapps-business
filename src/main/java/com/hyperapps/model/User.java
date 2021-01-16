package com.hyperapps.model;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	    public int user_id;
	    public String name;
	    public String email;
	    public String business_name;
	    public String business_short_desc;
	    public String business_long_desc;
	    public int parent_id;
	    public int user_group;
	    public List<Team_members> team_members;
	    public Welcome_content welcome_content;
	    public int store_status;
	    public int store_id;
	    public int isOwner;
	    public int teamMemberCount;

	    @Getter
	    @Setter
	    public static class Team_members{
	        public int id;
	        public String name;
	        public String email;
	        public int status;
	        public int level;
	        public String created_at;
	        public String updated_at;
	        public String mobile;
	        public int parent_id;
	        public int user_group;
	    }

	    @Getter
	    @Setter
	    public static class Welcome_content {
	        public int designation;
	        public String message;
	    }


}
