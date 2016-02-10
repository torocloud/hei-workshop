package io.toro.workshop.connectors;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.Cell;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;

import io.toro.workshop.blog.Blog;

/**
 * Google Sheets Connector<br/>
 * Uses Google SDK<br/>
 * <br/>
 * See Google API Documentation
 * <a href= "https://developers.google.com/google-apps/spreadsheets/?hl=en">here
 * <a/>
 * 
 * @author ericdaluz
 * @author Google
 */
public class GoogleSheetsConnector {

	private static SpreadsheetService service = new SpreadsheetService("MySpreadsheetIntegration");
	private static final String BLOG_SHEET_URL = "https://spreadsheets.google.com/feeds/list/1G-5h4o0SPJ3nFL68k14tVGddaxM_lVF6S9djo5boxmc/od6/private/full";
	private static final String CELLFEED_URL = "https://spreadsheets.google.com/feeds/cells/1G-5h4o0SPJ3nFL68k14tVGddaxM_lVF6S9djo5boxmc/od6/private/full";

	private static GoogleCredential getAccessTokens() throws Exception {
		GoogleCredential credential = new GoogleCredential.Builder()
				.setClientSecrets("271685667525-0o2jhghl7fts88vojtjg0n5ql3hd6ueo.apps.googleusercontent.com",
						"bEof69SyqLx56a5Kx1oko_PB")
				.setTransport(new NetHttpTransport()).setJsonFactory(new JacksonFactory()).build()
				.setAccessToken("ya29.dAJBb45YNoRCbp0-OLFxf-pOcKYIbgu5_06OhTqgRIcglclQacuFaY-ThVeT5F8ZVrYXdqE")
				.setRefreshToken("1/B1PbI8PloZ4_UtrJPIvtJaZb0Jddx4GHPLxqPggEaWJIgOrJDtdun6zK6XiATCKT");

		credential.refreshToken();
		return credential;
	}

	private static SpreadsheetService client() throws Exception {
		service.setProtocolVersion(SpreadsheetService.Versions.V3);
		service.setOAuth2Credentials(getAccessTokens());
		service.setHeader("User-Agent", "MySpreadsheetIntegration-v1");
		return service;
	}

	/**
	 * GET CELL FEED
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public static CellFeed getCellFeed(String query) throws Exception {
		return client().getFeed(new URL(CELLFEED_URL + query), CellFeed.class);
	}

	/**
	 * ADD A LIST ROW
	 * 
	 * @param long1
	 * @param blogTitle
	 * @param blogContent
	 * @throws Exception
	 */
	public static void addListRow( Long blogId, String blogTitle, String blogContent) throws Exception {
		client().getFeed(new URL(BLOG_SHEET_URL), ListFeed.class);
		ListEntry row = new ListEntry();
		row.getCustomElements().setValueLocal("BlogID", blogId.toString());
		row.getCustomElements().setValueLocal("BlogTitle", blogTitle);
		row.getCustomElements().setValueLocal("BlogContent", blogContent);
		row = service.insert(new URL(BLOG_SHEET_URL), row);
	}

	public static void deleteRow(int rowNum) {
		ListFeed listFeed;
		try {
			listFeed = client().getFeed(new URL(BLOG_SHEET_URL), ListFeed.class);
			ListEntry row = listFeed.getEntries().get(rowNum - 2);
			row.delete();
		} catch (Exception e) {
			System.err.println("No Blog with that ID!");
		}
	}

	/**
	 * FIND THE INDEX OF THE SAVED BLOG ON GSHEETS
	 * 
	 * @param blogID
	 * @return
	 * @throws Exception
	 */
	public static Integer findIndex( long blogID ) throws Exception {
		int index = 0;
		for (CellEntry cell : getCellFeed("?min-row=2&min-col=1&max-col=1").getEntries()) {
			if (Long.valueOf(cell.getCell().getValue()) == blogID )
				index = cell.getCell().getRow();
		}
		return index;
	}

	/**
	 * GET THE SAVED BLOG
	 * 
	 * @param indexNum
	 * @return
	 * @throws Exception
	 */
	public static Blog getRowByID(int indexNum) {
		Blog blog = new Blog();
		try {
			int ctr = 0;
			List<CellEntry> cellEntries = getCellFeed(
					"?min-row=" + indexNum + "&max-row=" + indexNum + "&min-col=1&max-col=3").getEntries();
			for (CellEntry cellEntry : cellEntries) {
				Cell cell = cellEntry.getCell();
				if (ctr == 0) {
					blog.setId(Long.valueOf(cell.getValue()));
					ctr++;
				} else if (ctr == 1) {
					blog.setTitle( cell.getValue() );
					ctr++;
				} else if (ctr == 2) {
					blog.setContent( cell.getValue() );
					ctr = 0;
				}
			}
		} catch (Exception e) {
			System.err.println("No Blog with that ID!");
		}
		return blog;
	}

	public static List<Blog> getAllCells() throws Exception {
		int ctr = 0;
		List<CellEntry> cellEntries = getCellFeed("?min-row=2&min-col=1&max-col=3").getEntries();
		List<Blog> list = new ArrayList<>();
		Blog blog = new Blog();
		for (CellEntry cellEntry : cellEntries) {
			Cell cell = cellEntry.getCell();
			if (ctr == 0) {
				blog.setId(Long.valueOf(cell.getValue()));
				ctr++;
			} else if (ctr == 1) {
				blog.setTitle( cell.getValue() );
				ctr++;
			} else if (ctr == 2) {
				blog.setContent( cell.getValue() );
				list.add(blog);
				blog = new Blog();
				ctr = 0;
			}
		}
		return list;
	}
}
