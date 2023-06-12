package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import vo.Movie;

public class SampleService {

    private static final String API_KEY = "45ac471b35ca42c983d971a438b31d25";
    private static final String API_URL = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&ServiceKey=Y40OV2CFS1I2MTV081VG";
    private static final JSONParser JSON_PARSER = new JSONParser();

    public List<Movie> getMovies() {

        List<Movie> movies = new ArrayList<Movie>();

        try {
            KobisOpenAPIRestService service = new KobisOpenAPIRestService(API_KEY);
            String yesterday = LocalDate.now().minus(1, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String dailyResponse = service.getDailyBoxOffice(true, yesterday, "10", "", "", "");
            JSONObject jsonObject = (JSONObject) JSON_PARSER.parse(dailyResponse);
            JSONObject parseBoxOfficeResult = (JSONObject) jsonObject.get("boxOfficeResult");
            JSONArray parseDailyBoxOfficeList = (JSONArray) parseBoxOfficeResult.get("dailyBoxOfficeList");

            for (Object dailyBoxOfficeObj : parseDailyBoxOfficeList) {
                JSONObject dailyBoxOffice = (JSONObject) dailyBoxOfficeObj;
                Movie movie = new Movie();
                movie.setRank(Integer.parseInt((String) dailyBoxOffice.get("rank")));
                movie.setTitle((String) dailyBoxOffice.get("movieNm"));
                movie.setReleaseDate(dailyBoxOffice.get("openDt").toString());
                movie.setAudiCnt(Integer.parseInt((String) dailyBoxOffice.get("audiAcc")));
                movies.add(movie);
            }

            for (Movie movie : movies) {
            	
                String apiUrl = String.format(
                		"%s&title=%s&releaseDts=%s", 
                		API_URL, 
                		URLEncoder.encode(movie.getTitle(), "UTF-8"), 
                		URLEncoder.encode(movie.getReleaseDate().replace("-",""), "UTF-8")
        		);
                
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                BufferedReader rd;
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                StringBuilder sb = new StringBuilder();
                String kmdbApi;
                while ((kmdbApi = rd.readLine()) != null) {
                    sb.append(kmdbApi);
                }
                rd.close();
                conn.disconnect();

                String movieDetail = sb.toString();
                System.out.println(movieDetail);
                Object obj = JSON_PARSER.parse(movieDetail);
                JSONObject result = (JSONObject) obj;
                JSONArray result1 = (JSONArray) result.get("Data");
                result = (JSONObject) result1.get(0);
                result1 = (JSONArray) result.get("Result");
                result = (JSONObject) result1.get(0);
                String posters = (String) result.get("posters");
                String[] poster = posters.split("\\|");
                movie.setPosterURL(poster[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

}