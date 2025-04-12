package ca.bhavik.assignment3.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private String Title;
    private String Year;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private String Poster;
    private String imdbRating;
    private String imdbID;
    private String Response;
    private String Studio; // Added Studio field
    private String id;     // Added for Firestore document ID

    // Getters and Setters
    public String getTitle() { return Title; }
    public void setTitle(String title) { Title = title; }

    public String getYear() { return Year; }
    public void setYear(String year) { Year = year; }

    public String getRated() { return Rated; }
    public void setRated(String rated) { Rated = rated; }

    public String getPlot() { return Plot; }
    public void setPlot(String plot) { Plot = plot; }

    public String getPoster() { return Poster; }
    public void setPoster(String poster) { Poster = poster; }

    public String getImdbRating() { return imdbRating; }
    public void setImdbRating(String imdbRating) { this.imdbRating = imdbRating; }

    public String getImdbID() { return imdbID; }
    public void setImdbID(String imdbID) { this.imdbID = imdbID; }

    public String getStudio() { return Studio != null ? Studio : "N/A"; }
    public void setStudio(String studio) { Studio = studio; }

    public String getProduction() { return getStudio(); } // Updated to return Studio

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}