package org.supply.app.entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ClientInfoStatus;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public class Entity
{
    private int id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Date birthday;
    private Date regDate;
    private String email;
    private String phone;
    private char gender;
    private String photoPath;

    private ImageIcon image;

    public Entity(int id, String firstname, String lastname, String patronymic, Date birthday, Date regDate, String email, String phone, char gender, String photoPath) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.regDate = regDate;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.photoPath = photoPath.replaceAll(Pattern.quote("\\"), "/");
        this.image = null;
        this.initImage();
    }

    private void initImage()
    {
        URL url = Entity.class.getClassLoader().getResource(photoPath);
        try {
            this.image = new ImageIcon(ImageIO.read(url));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if(this.image == null)
        {
            try{
                this.image = new ImageIcon(ImageIO.read(Entity.class.getClassLoader().getResource("Ссылка на заглущку")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id && gender == entity.gender && Objects.equals(firstname, entity.firstname) && Objects.equals(lastname, entity.lastname) && Objects.equals(patronymic, entity.patronymic) && Objects.equals(birthday, entity.birthday) && Objects.equals(regDate, entity.regDate) && Objects.equals(email, entity.email) && Objects.equals(phone, entity.phone) && Objects.equals(photoPath, entity.photoPath) && Objects.equals(image, entity.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, patronymic, birthday, regDate, email, phone, gender, photoPath, image);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                ", regDate=" + regDate +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", photoPath='" + photoPath + '\'' +
                ", image=" + image +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
}

