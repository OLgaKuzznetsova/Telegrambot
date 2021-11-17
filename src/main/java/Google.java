package com.mycomp;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * Created by ankushgupta & modified for SO.
 */

public class Google {

    private static Sheets sheetsService;
    private String SPREADSHEET_ID = "1zUwM2rqsn6TLriLFHqOjzN9OLI5joQcribbLNtgTwts/edit#gid=0";
    private Map<String, String> dictionary = new HashMap<>();


    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String P12FILE = "D:\\bright-petal-331716-25cd7f7e6340.p12";
    private static final String SERVICE_ACCOUNT_EMAIL = "bott-456@bright-petal-331716.iam.gserviceaccount.com";
    private static final String APPLICATION_NAME = "BotT";

    private static final Logger LOGGER = LoggerFactory.getLogger(Google.class);


    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);

    private Credential getCredentials() throws URISyntaxException, IOException, GeneralSecurityException {
        /*URL fileURL = Google.class.getClassLoader().getResource("D:\\bright-petal-331716-25cd7f7e6340.p12");
        /*if (fileURL == null) {
            fileURL = (new File("D:\\bright-petal-331716-25cd7f7e6340.p12")).toURI().toURL();
        }*/
        //HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        /*Sheets.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountPrivateKeyFromP12File(new File("D:\\bright-petal-331716-25cd7f7e6340.p12"))
                .setServiceAccountScopes(SCOPES)
                .build();*/







        /*GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY,
                clientSecrets, SCOPES)
                .setDataStoreFactory(new MemoryDataStoreFactory())
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver())
                .authorize("user");*/
        return null;
    }


    //@Override
    public List<Object[]> readSheet(String nameAndRange, String key) throws GeneralSecurityException, IOException, URISyntaxException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = key;
        final String range = nameAndRange;
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport
                    .newTrustedTransport();
            InputStream in = Google.class.getResourceAsStream("/credentials.json");
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    JSON_FACTORY, new InputStreamReader(in)
            );
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setTransport(httpTransport)
                    .setJsonFactory(JSON_FACTORY)
                    .setServiceAccountId("da9f2d32ff344cf6de15bd0552641bdccf2da564")
                    .setClientSecrets(clientSecrets)
                    .setServiceAccountScopes(SCOPES).build();


            Sheets service =new  Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            //Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                    //.setApplicationName(APPLICATION_NAME)
                    //.build();

            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            List<List<Object>> values = response.getValues();

            int a = 2;
            List<Object[]> result = new LinkedList<>();
            Map<String, String> dictionary = new HashMap<>();
            if (values == null || values.isEmpty()) {
                return Collections.emptyList();
            } else {
                for (List row : values) {
                    if(row.size() >= a) {
                        Object[] objArr = new Object[a];
                        for(int i=0;i<a;i++) {
                            objArr[i] = row.get(0);
                        }
                        result.add(objArr);
                    }
                }
                for (List row : values) {
                    String term = row.get(0).toString();
                    String definition = row.get(1).toString();
                    dictionary.put(term, definition);
                    System.out.println(row.size());
                }
            }
            return result;

        } catch(Exception ex) {
            LOGGER.error("Exception while reading google sheet", ex);
        } finally {

        }
        System.out.println(dictionary);
        System.out.println(455);

        return null;
    }


        /*int a = returnRange.length;
        List<Object[]> result = new LinkedList<>();
        Map<String, String> dictionary = new HashMap<>();
        for (List row : values) {
            String term = row.get(0).toString();
            String definition = row.get(1).toString();
            dictionary.put(term, definition);
        }


        System.out.println(dictionary);
        //return result;
        /*} catch (Exception ex) {
            LOGGER.error("Exception while reading google sheet", ex);
        } finally {

        }
        return null;
    }*/

    public static void main(String[] args) throws GeneralSecurityException, IOException, URISyntaxException {
        Google reader = new Google();
        var a = reader.readSheet("Bot!A1:B2", "1zUwM2rqsn6TLriLFHqOjzN9OLI5joQcribbLNtgTwts");
        System.out.println(a);
    }
}
