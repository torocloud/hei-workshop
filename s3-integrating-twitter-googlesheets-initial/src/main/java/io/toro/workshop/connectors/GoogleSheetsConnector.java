package io.toro.workshop.connectors;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

import io.toro.workshop.config.properties.ConnectorProperties;
import io.toro.workshop.exception.ResourceNotFoundException;

public class GoogleSheetsConnector implements InitializingBean {

	/*
	 * TODO constructors and other initialization here
	 */
	
    /**
     * Initialize {@link #service} after {@link #gsheetsProperties} is set.
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        GoogleCredential credential = new GoogleCredential.Builder()
                .setClientSecrets( null /*insert getter for ClientId*/, null /*insert getter for ClientSecret*/ )
                .setTransport( new NetHttpTransport() )
                .setJsonFactory( new JacksonFactory() )
                .build();
        credential.setAccessToken( null /*insert getter for Access Token*/ )
                .setRefreshToken( null /*insert gettr fot Refresh Token*/ );

        service = new SpreadsheetService( "MySpreadSheetIntegration" );
        service.setProtocolVersion( SpreadsheetService.Versions.V3 );
        service.setHeader( HttpHeaders.USER_AGENT, "MySpreadsheetIntegration-v1" );
        service.setOAuth2Credentials( credential );

       /*
        * TODO Set default Worksheet here
        */

    }

   /*
    * TODO GoogleSheets API Methods here
    */

}
