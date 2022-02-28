package com.example.ibm;

//Data container for the items coming in the GET request
public class Item {
    String Guid;
    String Email;
    String UserName;
    String Description;
    String Title;
    String AvatarUrl;
    String MediaType;
    String Created;
    int DurationInSec;

    public Item(String guid, String email, String userName, String description, String title, String avatarUrl, String mediaType, String created, int durationInSec) {
        Guid = guid;
        Email = email;
        UserName = userName;
        Description = description;
        Title = title;
        AvatarUrl = avatarUrl;
        MediaType = mediaType;
        Created = created;
        DurationInSec = durationInSec;
    }



}
