package com.jeseg.admin_system.company.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

public class Company {
    String name;
    boolean status;
    List<String> users;
    String logoUrl;
    String color;
}
