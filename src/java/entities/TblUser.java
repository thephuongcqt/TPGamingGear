/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import constant.AppConstant;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author PhuongNT
 */
@Entity
@Table(name = "TblUser", catalog = "NTPGamingGear", schema = "dbo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User", propOrder = { //    "fullName",
//    "password",
//    "isActive",
//    "role"
})
@XmlRootElement(name = "UserType", namespace = "www.user.vn")
@NamedQueries({
    @NamedQuery(name = "TblUser.findAll", query = "SELECT t FROM TblUser t"),
    @NamedQuery(name = "TblUser.findByEmail", query = "SELECT t FROM TblUser t WHERE t.email = :email"),
    @NamedQuery(name = "TblUser.findByFullName", query = "SELECT t FROM TblUser t WHERE t.fullName = :fullName"),
    @NamedQuery(name = "TblUser.findByPassword", query = "SELECT t FROM TblUser t WHERE t.password = :password"),
    @NamedQuery(name = "TblUser.findByIsActive", query = "SELECT t FROM TblUser t WHERE t.isActive = :isActive"),
    @NamedQuery(name = "TblUser.findByRole", query = "SELECT t FROM TblUser t WHERE t.role = :role"),
    @NamedQuery(name = "TblUser.checkLogin", query = "SELECT t FROM TblUser t WHERE t.email = :email AND t.password = :password AND t.isActive = :isActive")})
public class TblUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Email", nullable = false, length = 100)
    @XmlAttribute(name = "Email", required = true)
    private String email;

    @Column(name = "FullName", length = 250)
    @XmlElement(name = "FullName", required = true, namespace = "www.user.vn")
    private String fullName;

    @Column(name = "Password", length = 100)
    @XmlElement(name = "Password", required = true, namespace = "www.user.vn")
    private String password;

    @Column(name = "IsActive")
    @XmlAttribute(name = "IsActive", required = false)
    private Boolean isActive;

    @Column(name = "Role")
    @XmlElement(name = "Role", defaultValue = "2", required = false, namespace = "www.user.vn")
    private Integer role;

    @PrePersist
    void preInsert() {
        if (this.isActive == null) {
            this.isActive = true;
        }
        if(this.role == null){
            this.role = AppConstant.defaultRole;
        }
    }

    public TblUser(String email, String fullName, String password, Boolean isActive, Integer role) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.isActive = isActive;
        this.role = role;
    }

    public TblUser(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    public TblUser() {
    }

    public TblUser(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblUser)) {
            return false;
        }
        TblUser other = (TblUser) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TblUser[ email=" + email + " ]";
    }
}
