package view;

import java.util.ArrayList;
import java.util.Comparator;

public class SongList
{
    private ArrayList<Songs> song ;

    public SongList()
    {
        song = new ArrayList<>();
    }

    /*@Override
    public String toString()
    {
        ArrayList<Songs> loop = getSongList();
        String name, album, year, artist;
        for (int i =0; i< song.size(); i++)
        {
            name = loop.get(i).getName();
            album = loop.get(i).getAlbum();
            year = loop.get(i).getYear();
            artist = loop.get(i).getArtist();
            System.out.println( "Name: "+name +" Artist: " +artist +" Album: "+ album+ " Year: "+ year);
        }



    }*/

    /*
    *   This method is to store a new object of type SONGS
    * */

    public void setSong(String name, String Album, String Year, String Artist)
    {
        song.add(new Songs(name,Album,Year,Artist));
    }


    /*
    * This Method is to check if the song being added is not a duplicate
    *
    * To use this it would require that when the USER adds a song it saves the data
    * in an variable of type: SONGS
    * then, pass it to this function
    * */
    private int findSong(Songs songIn)
    {
        return this.song.indexOf(songIn);
    }

    private int findSong(String Name)
    {
        for(int i =0;i<song.size();i++)
        {
            Songs test = this.song.get(i);

            if(test.getName().equals(Name))
                return i;
        }
        return -1;
    }
    ////////////////////////////////////////////////////////////////////////////////////


    public ArrayList<Songs> getSongList()
    {
        return song;
    }

    public void sortSongs()
    {
        if (song.size()==0)
            return;

        /*
        *    Lets see if soring first by artist then by song name does the trick
        *
        * */
        song.sort(Comparator.comparing(Songs::getArtist));
        song.sort(Comparator.comparing(Songs::getName));
        //Need to Loop through this sorted array list and see if there are any
        //name duplicates and if so sort the songs with duplicate song names by artist and swap there
        //respective indices in the ArrayList.

        /*for (int i=1;i<song.size();i++)
        {
            if (song.get(i).name.compareTo(song.get(i+1)))
        }*/
    }

    public void editSong(int index, String name, String Album, String Year, String Aritst)
    {
        song.get(index).setAlbum(Album);
        song.get(index).setArtist(Aritst);
        song.get(index).setName(name);
        song.get(index).setYear(Year);
    }

    /*
    *   This method takes a Variable of type Songs from Controller
    *   then checks if it exists.
    *   if it exist in the arraylist then we update
    * */
    public boolean editSong(Songs songIn, Songs songReplace)
    {
        if(findSong(songIn)>=0)
        {
            song.get(song.indexOf(songIn)).setAlbum(songReplace.getAlbum());
            song.get(song.indexOf(songIn)).setArtist(songReplace.getArtist());
            song.get(song.indexOf(songIn)).setName(songReplace.getName());
            song.get(song.indexOf(songIn)).setYear(songReplace.getYear());

            return true;
        }
        return false;
    }
    public boolean removeSong(Songs songIn)
    {
        if(findSong(songIn)>=0)
        {
            song.remove(song.indexOf(songIn));
            return true;
        }
        return false;
    }

    public void printSongList()
    {
        String name, album, year, artist;
        for (int i =0;i<this.song.size();i++)
        {
            name = song.get(i).getName();
            album = song.get(i).getAlbum();
            year = song.get(i).getYear();
            artist = song.get(i).getArtist();
            System.out.println( "Name: "+name +" Artist: " +artist +" Album: "+ album+ " Year: "+ year);
        }
    }

}
