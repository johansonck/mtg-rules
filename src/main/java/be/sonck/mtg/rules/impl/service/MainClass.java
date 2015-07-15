package be.sonck.mtg.rules.impl.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by johansonck on 04/05/15.
 */
public class MainClass {
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-beans.xml");

    public static void main(String[] args) throws IOException {
        System.out.println(getLine());
    }

    private static String getLine() throws IOException {
        try (BufferedReader reader = (RulesResourceReader) applicationContext.getBean("rulesResourceReader")) {
            for (int i = 0; i < 6; i++) {
                reader.readLine();
            }

            return reader.readLine();
        }
    }
}
