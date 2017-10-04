package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Comparator;
import java.util.Optional;

public class Controller
{

    private final ObservableList<Songs> songsArray = FXCollections.observableArrayList();


    @FXML
    ListView<Songs> songListView;

    @FXML
    Button addButton, delButton, editButton;

    @FXML
    TextField inputArtist, inputSong, inputAlbum, inputYear, displayArtist, displayAlbum, displaySong,displayYear;

    @FXML
    public void start(Stage mainStage) {
        //Do the call
        songListView.setItems(songsArray);
        songListView.getSelectionModel().select(0);

        songListView.getSelectionModel()
                .selectedIndexProperty()
                .addListener(
                        (obs, oldVal, newVal) ->
                                showItemInputDialog(mainStage));


    }




    public void createSong(ActionEvent event) {
        if (inputArtist.getText().isEmpty() || inputSong.getText().isEmpty()) {
            Alert inputError = new Alert(Alert.AlertType.ERROR);
            inputError.setTitle("Try again.");
            inputError.setContentText("Artist and Song fields must not be empty");
            inputError.showAndWait();

        } else {
            addSong(inputSong.getText(),inputAlbum.getText(),inputYear.getText(),inputArtist.getText());
            sortSongs();

        }
    }


    public void deleteSong(ActionEvent event)
    {

    }


    private void showItemInputDialog(Stage mainStage) {
        Songs item = songListView.getSelectionModel().getSelectedItem();
        int index = songListView.getSelectionModel().getSelectedIndex();

        displayAlbum.setText(item.getAlbum());
        displayArtist.setText(item.getArtist());
        displaySong.setText(item.getName());
        displayYear.setText(item.getYear());
//
//        Optional<String> result = dialog.showAndWait();
//        if (result.isPresent()) { obsList.set(index, result.get()); }
    }


/*
*   This method is to add the song to the SongList
*
*   !!!!!!!!! We need to find a way to get the:
*       NAME, ALBUM, YEAR, ARTIST from the TEXTFIELDS
*       ONLY IFF they click on add
*
* */

    public void addSong(String name, String Album, String Year, String Artist)
    {
        Songs temp = new Songs(name,Album,Year, Artist);
        if(songsArray.indexOf(temp)>=0)
        {
            //Means that there is a duplicate so populate ERROR popup
        }
        else
        {
            songsArray.add(temp);
        }
    }

    public void removeSong(String name, String Album, String Year, String Artist)
    {
        Songs temp = new Songs(name,Album,Year, Artist);
        if(songsArray.indexOf(temp)>=0)
        {
            songsArray.remove(songsArray.indexOf(temp));//Check if this works!!!
        }
        else
        {
            //Means that there isn't a song like that in the list so populate ERROR popup
        }
    }

    public void editSong(Songs old, Songs replacement)
    {

        if(songsArray.indexOf(old)>=0)
        {
            removeSong(old.getName(),old.getAlbum(),old.getYear(),old.getArtist());
            addSong(replacement.getName(),replacement.getAlbum(),replacement.getYear(),replacement.getArtist());
        }
        else
        {
            //Means that there isn't a song like that in the list so populate ERROR popup
        }
    }

/*
*   This method is going to be used after any modifications are made to the SONGS
*   and BEFORE we save the LIST.
*
*   VERY IMPORTANT PART OF THE ASSIGNMENT
* */
    public void sortSongs()
    {
        if (songsArray.size()==0)
            return;

        /*
        *    Lets see if soring first by artist then by song name does the trick
        *
        * */
        songsArray.sort(Comparator.comparing(Songs::getArtist));
        songsArray.sort(Comparator.comparing(Songs::getName));
        //Need to Loop through this sorted array list and see if there are any
        //name duplicates and if so sort the songs with duplicate song names by artist and swap there
        //respective indices in the ArrayList.

        /*for (int i=1;i<song.size();i++)
        {
            if (song.get(i).name.compareTo(song.get(i+1)))
        }*/
    }


/*
*   This method is what will be used to store the ObservableList into an XML
*   This needs to be called on before the EXIT of the program to store the data
*   to long term Memory.!!!!!!!!!!!!!!!!!!!
*
*   ----------Accepts a ArrayList of Song objects--------------
*   */

public void createXML(ObservableList<Songs> songs)
{
    try {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("songList");
        doc.appendChild(rootElement);




        for(int i = 0; i<songs.size();i++)
        {
            String nameIn = songs.get(i).getName();
            String artistIn = songs.get(i).getArtist();
            String albumIn = songs.get(i).getAlbum();
            String yearIn = songs.get(i).getYear();

            // staff elements
            Element song = doc.createElement("Song");
            rootElement.appendChild(song);

            // set attribute to staff element
            Attr attr = doc.createAttribute("id");
            //attr.setValue("1");
            attr.setValue(Integer.toString(i));
            song.setAttributeNode(attr);

            // shorten way
            // staff.setAttribute("id", "1");

            // firstname elements
            Element songname = doc.createElement("name");
            songname.appendChild(doc.createTextNode(nameIn));
            song.appendChild(songname);

            // lastname elements
            Element artist = doc.createElement("artist");
            artist.appendChild(doc.createTextNode(artistIn));
            song.appendChild(artist);

            // nickname elements
            Element album = doc.createElement("album");
            album.appendChild(doc.createTextNode(albumIn));
            song.appendChild(album);

            // salary elements
            Element year = doc.createElement("year");
            year.appendChild(doc.createTextNode(yearIn));
            song.appendChild(year);
        }
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("file.xml"));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);

        System.out.println("File saved!");

    } catch (ParserConfigurationException pce) {
        pce.printStackTrace();
    } catch (TransformerException tfe) {
        tfe.printStackTrace();
    }
}



/*
*   You need to call this method when program starts to populate the
*   LISTVIEW with the saved data.
* */
public void getXML()
{
    try {

        File fXmlFile = new File("file.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("Songs");

        System.out.println("----------------------------");


        String name, artist, album, year;
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                //^^^^^^^^^^^^^^^^^^^^ This is for debug ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
                System.out.println("Song id : " + eElement.getAttribute("id"));
                System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("Artist : " + eElement.getElementsByTagName("artist").item(0).getTextContent());
                System.out.println("Album : " + eElement.getElementsByTagName("album").item(0).getTextContent());
                System.out.println("Year : " + eElement.getElementsByTagName("year").item(0).getTextContent());

                //^^^^^^^^^^^^^^^^^^^^ This is for debug ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//

                name = eElement.getElementsByTagName("name").item(0).getTextContent();
                artist = eElement.getElementsByTagName("artist").item(0).getTextContent();
                album = eElement.getElementsByTagName("album").item(0).getTextContent();
                year = eElement.getElementsByTagName("year").item(0).getTextContent();

                addSong(name,album,year,artist);
            }
        }
    } catch (Exception e)
    {
        e.printStackTrace();

    }
}

}
