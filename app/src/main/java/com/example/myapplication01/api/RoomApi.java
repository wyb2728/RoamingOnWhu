package com.example.myapplication01.api;

import com.example.myapplication01.models.Member;
import com.example.myapplication01.models.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RoomApi {

    @POST("Room/createOrJoin")
    Call<Room> createOrJoinRoom(@Query("key") String key, @Query("memberId") String memberId,
                                @Query("latitude") double latitude, @Query("longitude") double longitude);

    @POST("Room/updateLocation")
    Call<Void> updateLocation(@Query("key") String key, @Query("memberId") String memberId,
                              @Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("Room/getMembersLocation")
    Call<List<Member>> getMembersLocation(@Query("key") String key);

    @POST("Room/leave")
    Call<Void> leaveRoom(@Query("key") String key, @Query("memberId") String memberId);
}
