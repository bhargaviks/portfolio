package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import java.util.List;

public final class FindMeetingQuery {

  public static final int fullDay = 24 * 60;
  /**
    This function is responsible for adding the "find a meeting" feature.
    @param events has a collection of the events happening during the day
    @param request has details of the meeting that is required to be scheduled.
    @return a Collection that has all the possible timeRanges for te requested meeting.
  */
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    
    //first, check all attendees.
    Collection<String> mandatoryAttendees = request.getAttendees();
    Collection<String> optionalAttendees = request.getOptionalAttendees();
    mandatoryAttendees.addAll(optionalAttendees);

    MeetingRequest allRequest = new MeetingRequest(mandatoryAttendees, request.getDuration());
    Collection<TimeRange> withOptionalTimes = Times(events, allRequest);

    if( withOptionalTimes!= null && withOptionalTimes.size() == 0)
      return Times(events,request);
    else
      return withOptionalTimes;
    
  }

public Collection<TimeRange> Times(Collection<Event> events, MeetingRequest request){

  // This is the duration of the request meeting
  long meetingDuration = request.getDuration();   

  // This is a collection of strings that have the names of all the attendees in the to be set meeting   
  Collection<String> meetingAttendees = request.getAttendees();    

  if (!(meetingDuration <= TimeRange.WHOLE_DAY.duration()))
    return Arrays.asList();

  else if(events.isEmpty()) 
    return Arrays.asList(TimeRange.WHOLE_DAY);
    
  // This ArrayList contains all the unavailable times of TimeRanges at which the request meeting cannot be set. 
  // This is calculated or set by taking each event from the collection, and checking if the event attendees are busy in the collection
  // of meetings, and hence, can't be available for the meeting request.  
  ArrayList<TimeRange> unavailableTimes = new ArrayList<TimeRange>();

  for (Event event : events){
    Collection<String> eventAttendees = event.getAttendees();
    TimeRange rangeOfEvent = event.getWhen();
  
    // Checking the attendees of the meeting request 
    for (String attendee : meetingAttendees) {    
      if (eventAttendees.contains(attendee)){
        // Comes here only if even one of the members of the request cannot make it
        unavailableTimes.add(rangeOfEvent);           
        break;                                    
      } 
    }
  }

  Collections.sort(unavailableTimes, TimeRange.ORDER_BY_START);

  // At this time, we have a list of timeRanges in unavailableTimes where the meeting cannot be set that's is sorted by order of start time. 
  // Now let us traverse the timeranges where the meeting can be set within the working hours. 

  // This will have the times that have a collection of all the available timeranges of events
  Collection<TimeRange> availableTimes = new ArrayList<TimeRange>();  

  // This variable behaves like an iterator throughout the day. So, it starts from 0, which is the START time.  
  int progress = TimeRange.START_OF_DAY;            
  
  for(TimeRange rangeOfEvent : unavailableTimes){
    int rangeEnd = rangeOfEvent.end();
    int rangeStart = rangeOfEvent.start();

    if (progress + meetingDuration <= rangeStart){
    // If there can be a possible meeting with the requested meeting duration and that starts from progress, then it's valid.
      TimeRange availableRange = TimeRange.fromStartEnd(progress, rangeStart, false);
      availableTimes.add(availableRange);
    }
    progress = progress+ (int)meetingDuration;    
  }

  if (progress + meetingDuration <= fullDay){
    TimeRange endOfDayMeeting = TimeRange.fromStartEnd(progress, fullDay , false);
    availableTimes.add(endOfDayMeeting);
  }
  
  return availableTimes;
  }
}
