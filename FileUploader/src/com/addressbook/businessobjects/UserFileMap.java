package com.addressbook.businessobjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

/**
 * Business object class, also used to represent userId:fileId mapping
 * @author kashifu
 *
 */

@NamedNativeQueries({
	@NamedNativeQuery(
			name = "findMaxFileIdForUser",
			query = "select max(m.fileId) from USER_FILE_MAPPINGS m where m.userId = :userId",
			resultClass = UserFileMap.class
	)
})
@Entity
@Table(name = "USER_FILE_MAPPINGS")
public class UserFileMap {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "userId")
	private int userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserFileMap [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
