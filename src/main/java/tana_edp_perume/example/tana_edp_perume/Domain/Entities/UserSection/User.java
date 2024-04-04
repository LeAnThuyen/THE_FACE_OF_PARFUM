package tana_edp_perume.example.tana_edp_perume.Domain.Entities.UserSection;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "User")
public class User {

    private long Id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private String Avatar;
    private Date CreationTime ;
    public User() {

    }
    public User(long id, String firstName,String lastName,String email,String password,String avatar,Date creationTime){
    this.Id=id;
    this.FirstName=firstName;
    this.LastName=lastName;
    this.Email=email;
    this.Avatar=avatar;
    this.CreationTime=creationTime;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return Id;
    }
    public void setId(long id) {
        this.Id  = id;
    }

    @Column(name = "FirstName", nullable = false)
    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    } @Column(name = "LastName", nullable = false)
    public String getLastName() {
        return LastName;
    }
    public void setLastName(String lastName) {
        this.LastName = lastName;
    }
    @Column(name = "Email", nullable = false)
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        this.Email = email;
    }
    @Column(name = "Password", nullable = false)
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        this.Password = password;
    }
    @Column(name = "Avatar", nullable = false)
    public String getAvatar() {
        return Avatar;
    }
    public void setAvatar(String avatar) {
        this.Avatar = avatar;
    }
    @Column(name = "CreationTime", nullable = false)
    public Date getCreationTime() {
        return CreationTime;
    }
    public void setCreationTime(Date creationTime) {
        this.CreationTime = creationTime;
    }
}
