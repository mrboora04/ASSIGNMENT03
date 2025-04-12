package ca.bhavik.assignment3.model;

import java.util.List;

public class MovieResponse {
    private String Response;
    private String Error;
    private List<Movie> Search;

    public String getResponse() { return Response; }
    public void setResponse(String response) { Response = response; }
    public String getError() { return Error; }
    public void setError(String error) { Error = error; }
    public List<Movie> getSearch() { return Search; }
    public void setSearch(List<Movie> search) { Search = search; }
}