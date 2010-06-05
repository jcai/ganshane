/* 
 * Copyright 2010 The Ganshane Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ganshane.user.entities;

import corner.orm.base.BaseEntity;

/**
 * 用户实体类
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 * @hibernate.class table="OA_B_SCHEMA_INFO"  
 * @hibernate.comment 用户实体类
 * @hibernate.meta attribute="class-description" value="通信录（OA_I_AddressBook）."
 */
public class User extends BaseEntity{

    /** **/
    private static final long serialVersionUID = 858657213006695743L;
    /** 
     *
     * @hibernate.column name="name" comment="Unique user name."
     * @hibernate.id generator-class="uuid"
     **/
    private String name;
    /**
     * @hibernate.property
     * @hibernate.column name="pass" comment="User's password (md5 hash) " length=50
     * @hibernate.meta attribute="field-description" value="密码."
     **/
    private String pass;
    /** User's email address **/
    private String mail;
    /** Per-user comment display mode (threaded vs. flat), used by the {comment} module **/
    private int mode;
    /** Per-user comment sort order (newest vs. oldest first), used by the {comment} module. **/
    private int sort;
    /** User's signature. **/
    private String signature;
    /** Timestamp for when user was created. **/
    private long created;
    /** 'Timestamp for previous time user accessed the site.. **/
    private long access;
    /** Timestamp for user's last login. **/
    private long login;
    /** Whether the user is active(1) or blocked(0). **/
    private int status;
    /** Path to the user's uploaded picture. **/
    private String picture;
    /** A serialized array of name value pairs that are related to the user. 
     * Any form values posted during user edit are stored and are loaded into the user object during user_load(). 
     **/
    private String data;
    /**
     * @return the name
     */
    //@Column(name="name",length=60,nullable=false)
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the pass
     */
    //@Column(name="pass",length=32,nullable=false)
    public String getPass() {
        return pass;
    }
    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    /**
     * @return the mail
     */
    //@Column(name="mail",length=64)
    public String getMail() {
        return mail;
    }
    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }
    /**
     * @return the mode
     */
    //@Column(name="mode",nullable=false)
    public int getMode() {
        return mode;
    }
    /**
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
    }
    /**
     * @return the sort
     */
    //@Column(name="sort",nullable=false)
    public int getSort() {
        return sort;
    }
    /**
     * @param sort the sort to set
     */
    public void setSort(int sort) {
        this.sort = sort;
    }
    /**
     * @return the signature
     */
    //@Column(name="signature",length=255,nullable=false)
    public String getSignature() {
        return signature;
    }
    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }
    /**
     * @return the created
     */
    //@Column(name="created")
    public long getCreated() {
        return created;
    }
    /**
     * @param created the created to set
     */
    public void setCreated(long created) {
        this.created = created;
    }
    /**
     * @return the access
     */
    //@Column(name="access")
    public long getAccess() {
        return access;
    }
    /**
     * @param access the access to set
     */
    public void setAccess(long access) {
        this.access = access;
    }
    /**
     * @return the login
     */
    //@Column(name="login")
    public long getLogin() {
        return login;
    }
    /**
     * @param login the login to set
     */
    public void setLogin(long login) {
        this.login = login;
    }
    /**
     * @return the status
     */
    //@Column(name = "status")
    public int getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    /**
     * @return the picture
     */
    //@Column(name="picture",length=255)
    public String getPicture() {
        return picture;
    }
    /**
     * @param picture the picture to set
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }
    /**
     * @return the data
     */
    //@Column(name="data")
   // @Lob   
    public String getData() {
        return data;
    }
    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }
}
