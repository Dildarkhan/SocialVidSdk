package com.dildarkhan.socialvidsdk.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResponseVideo{
    @SerializedName("msg")
    private List<Msg> msgList;

    public class Msg{
        public int tp;
        public String uid;
        public int liked;
        public int score;
        public String status;
        public int is_block;
        public String description;
        public String country;
        public String city;
        public String _id;
        public int id;
        public String fb_id;
        public UserInfo user_info;
        public Count count;
        public String video;
        public String thum;
        public String gif;
        public Sound sound;
        public Date created;
        public int __v;



        public class AudioPath{
            public String mp3;
            public String acc;
        }

        public class Count{
            public int like_count;
            public int video_comment_count;
            public int view;
            public String _id;
        }

        public class Sound{
            public int id;
            public String sound_name;
            public String description;
            public String thum;
            public String section;
            public String _id;
            public Date created;
            public AudioPath audio_path;
        }

        public class UserInfo{
            public String first_name;
            public String last_name;
            public String fb_id;
            public String profile_pic;
            public String gender;
            public String verified;
            public String _id;
            public String username;
        }

    }

}



