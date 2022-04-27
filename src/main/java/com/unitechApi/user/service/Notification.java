package com.unitechApi.user.service;

import com.unitechApi.user.model.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Notification {
   private User user;
   private String username;

}
