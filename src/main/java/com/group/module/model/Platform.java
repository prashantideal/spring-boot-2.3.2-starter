/**
 * 
 */
package com.group.module.model;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author prashant.mishra1
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Platform {
	WEB, IOS, ANDROID
}
