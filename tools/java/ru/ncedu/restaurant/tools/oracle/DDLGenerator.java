package ru.ncedu.restaurant.tools.oracle;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DDLGenerator {
    public DDLGenerator() {}

    public static void main(String args[]){
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("restaurant", properties);
        emf.createEntityManager().close();
        emf.close();
    }
}
