Administrator Client
~~~~~~~~~~~~~~~~~~~~

Setup
=====

1) Run the jar file with: ``java -jar Group2Standalone.jar``

2) On first run, there will be no data and it will initialize to empty

**Note:**
  This _requires_ `Java 7 <http://www.oracle.com/technetwork/java/javase/downloads/jdk-7u3-download-1501626.html>`_.

Specification Testing
=====================

12. The administrative user must be able to start the game (at this point no new
    players or contestants can be added). At this point the administrative 
    user should also enter the amount of money that each player is going to 
    pitch in (for example each player might be pitching in 5 dollars or 1 
    dollar).

    - Upon clicking Start Season (in the General Panel) you will be prompted for
      the bet amount


15. The administrative user must be able to add/modify/delete bonus questions 
    for each round/week. Each bonus question must be 1 to 200 characters long
    the question can be either multiple choice or short answer. The answer must 
    be 1 to 200 characters. The administrative user must be able to list all the
    bonus questions in order by round/week.

    - This information can be found within the "Bonus" tab.


16. The administrative user must be able to change which contestant was 
    eliminated each week, incase he/she made a mistake. However the player can 
    NOT change who he/she picked to eliminated one the round has passed. If the 
    admin user change the contestant that was eliminated for a previous week, 
    then the standings will have to be updated.
  
    - When changing a choice, go to the contestant tab, and click open the 
      combobox to the left of the "set status" button. This allows you to choose
      (after selecting the contestant in question within the display table) what
      week you'd like them to be cast off on. If it is the current week, it
      proceeds as normal, but if it's a previous week, you will be asked if you
      would like to continue, as it would invalidate the game.


17. If a player forgets to pick who will be eliminated in a week, then he/she 
    will be given a random contestant as his/her pick for that week (thus some 
    weeks the player might get points even though he/she didn't pick anyone, if 
    the random pick is the contestant who was eliminated). This also means that 
    a player who drops out (stops picking altogether) might end up winning the 
    pool.

    - This is generated when there are no picks select upon clicking "advance 
      week" within the general tab


18. Players pick which of the 3 remaining contestants will win. If they pick 
    correctly they get 40 points

    - This is generated within the backend after the final week's advancement. 
      This can be seen within the player tab's table, which displays player 
      scores


19. Every week players will say who they think will be eliminated. If the player
    pick the correct contestant who will be eliminated, they get 20 points

    - See 18.


20. A player can change who they think will win the whole game at any point, 
    then they get 2 points * the number of remaining contestants

    - See 18.


21. Stage 2, during the last week, when there are only 3 contestants left 
    Players pick which of the 3 remaining contestants will win. If they pick 
    correctly they get 40 points

    - See 18.


22. Stage 2, during the last week, when there are only 3 contestants left. 
    Players get the points for the overall winner if they picked correctly in an
    early round

    - See 18.


23. Stage 2, during the last week, when there are only 3 contestants leftPlayers
    get the points from each round for picking who would be eliminated

    - See 18.


24. All Stages, at any point during the game: At any point during the game, 
    players may answer additional bonus questions for bonus marks, for example 
    "Who wins immunity this week?", or "How many people received votes during 
    tribal counsel this week?". Each correct answer to each bonus question is 
    awarded 10 points.

    - See 18.


25. Assign a first place player, a second place player and third place player. 
    The first place player gets 60% of the amount of money, the second place 
    player gets 30% of the amount of money and the third place person gets 10% 
    of the amount of money.

    - Generated in backend upon advancing to the end of the game. This 
      information is displayed in the general tab upon completion of the game 
      under the "winners" panel.


26. At any time the admin user should be able to get all the players picks for a
    given week/round. This would normally be done by writing to the internet but
    because of issues with FTP, you can read and write to the C: drive (both 
    parts of the project will be tested on the same machine with the same c: 
    drive).

    - Backend of overall program.


