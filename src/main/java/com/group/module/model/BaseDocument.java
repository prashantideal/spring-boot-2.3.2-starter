/**
 * 
 */
package com.group.module.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
@Data
public class BaseDocument implements Serializable{
	
	@Id
	private String id;

    @Field("created_date")
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createDate;

    @Field("last_modified_date")
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime lastModifiedDate;

    @Field("created_by")
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    @Field("last_modified_by")
    @LastModifiedBy
    @JsonIgnore
    private String lastModifiedBy;
    
	@Field(name="deleted")
	private Boolean deleted = false;

}
