package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){
       /* User user=new User();
UserRepository.save(user);
return user.getId();*/

        User savedUser = userRepository.save(user);
        return savedUser.getId();

        //Jut simply add the user to the Db and return the userId returned by the repository

      //  return null;
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
          List<WebSeries> webSeriesList = webSeriesRepository.findAll();
          int count=0;
          User user = userRepository.findById(userId).get();
          for(WebSeries webSeries : webSeriesList) {
              if (user.getAge() >= webSeries.getAgeLimit()) {
                  if (user.getSubscription().getSubscriptionType() == SubscriptionType.ELITE) {
                      count++;
                  } else if (user.getSubscription().getSubscriptionType() == SubscriptionType.PRO && (
                          webSeries.getSubscriptionType() == SubscriptionType.PRO ||
                                  webSeries.getSubscriptionType() == SubscriptionType.BASIC
                  )) {
                      count++;
                  } else if (user.getSubscription().getSubscriptionType() == SubscriptionType.BASIC &&
                          webSeries.getSubscriptionType() == SubscriptionType.BASIC) {
                      count++;
                  }
              }
          }
             return count;

          }

}
