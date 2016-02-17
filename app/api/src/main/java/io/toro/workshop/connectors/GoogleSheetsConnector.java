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

import io.toro.workshop.exception.ResourceNotFoundException;
import io.toro.workshop.security.properties.ConnectorProperties;

public class GoogleSheetsConnector implements InitializingBean {

    private static final String SPREADSHEET_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

    private ConnectorProperties.GoogleSheets gsheetsProperties;
    private SpreadsheetService service;

    private String spreadsheetTitle = "Blogs";
    private WorksheetEntry currentWorksheet;

    @Autowired
    public void setGoogleSheetsProperties( ConnectorProperties properties ) {
        this.gsheetsProperties = properties.getGoogleSheets();
    }

    public void setSpreadsheetTitle( String spreadsheetTitle ) {
        this.spreadsheetTitle = spreadsheetTitle;
    }

    /**
     * Initialize {@link #service} after {@link #gsheetsProperties} is set.
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        GoogleCredential credential = new GoogleCredential.Builder()
                .setClientSecrets( gsheetsProperties.getClientId(), gsheetsProperties.getClientSecret() )
                .setTransport( new NetHttpTransport() )
                .setJsonFactory( new JacksonFactory() )
                .build();
        credential.setAccessToken( gsheetsProperties.getAccessToken() )
                .setRefreshToken( gsheetsProperties.getRefreshToken() );

        service = new SpreadsheetService( "MySpreadSheetIntegration" );
        service.setProtocolVersion( SpreadsheetService.Versions.V3 );
        service.setHeader( HttpHeaders.USER_AGENT, "MySpreadsheetIntegration-v1" );
        service.setOAuth2Credentials( credential );

        URL url = new URL( SPREADSHEET_FEED_URL );
        // fetch my spreadsheets feed (raw response)
        SpreadsheetFeed feed = service.getFeed( url, SpreadsheetFeed.class );
        // and look for a spreadsheet with title "Blogs"
        SpreadsheetEntry currentSheet = null;
        for ( SpreadsheetEntry entry : feed.getEntries() )
            if ( entry.getTitle().getPlainText().contains( spreadsheetTitle ) ) {
                currentSheet = entry;
                break;
            }
        if ( currentSheet == null )
            throw new ResourceNotFoundException( "Cannot find spreadsheet with name " + spreadsheetTitle );

        // then, fetch the first worksheet feed (raw response)
        WorksheetFeed worksheetFeed = service.getFeed( currentSheet.getWorksheetFeedUrl(), WorksheetFeed.class );
        // and get the first worksheet
        currentWorksheet = worksheetFeed.getEntries().get( 0 );
    }

    /**
     * @see https://developers.google.com/google-apps/spreadsheets/#retrieving_a_list-based_feed
     */
    public List<ListEntry> getRowEntries() throws ServiceException, IOException {
        ListFeed feed = service.getFeed( currentWorksheet.getListFeedUrl(), ListFeed.class );
        return feed.getEntries();
    }

    /**
     * @see https://developers.google.com/google-apps/spreadsheets/#deleting_a_list_row
     */
    public ListEntry addRowEntry( ListEntry entry ) throws ServiceException, IOException {
        ListEntry savedEntry = service.insert( currentWorksheet.getListFeedUrl(), entry );
        return savedEntry;
    }
}
