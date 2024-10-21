package com.deepak.expense_tracker_api.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categoryrequest {
	private String name;
	private String description ;
	private String icon;
	
	
}
