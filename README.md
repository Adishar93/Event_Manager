# Event_Manager_Android
An android app with the purpose of managing,sharing and updating events for all the app users in Realtime.
It uses Firebase storage NSQL for storing and retrieving the Event data in Realtime, and Firebase Authentication for authenticating users.

It has two types of user accounts:
1) Normal user: This user can only access data related to upcoming and past events.
2) Admin: This type of user can access data related to all upcoming events as well as edit and delete events that were created specifically bythat account

There is also an option for Normal users to register for an event, if it was specified as registerable by the creator of the event.
The feature to implement push notifications for new added upcoming events is incomplete for now.
