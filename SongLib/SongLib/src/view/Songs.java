package view;

import javafx.collections.ObservableArray;

import java.util.ArrayList;

public class Songs

{
    String name;
    String album;
    String year;
    String artist;


    public Songs(String name, String album, String year, String artist)
    {
        this.name = name;
        this.album = album;
        this.year = year;
        this.artist = artist;
    }

    @Override
    public String toString()
    {
        return name +  artist ;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public Songs createSong(String name, String album, String year, String artist)
    {
        return new Songs(name, album, year, artist);
    }
}
