package ua.svinkov.model.entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Topic entity
 * 
 * @author R.Svinkov
 *
 */
@Getter
@Setter
@ToString
@Builder
public class Topic implements Serializable {

	private static final long serialVersionUID = -1152859079593570366L;

	private int topicId;
	private String topic;

}
