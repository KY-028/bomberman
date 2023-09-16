This game is a slightly simpler and modified version of the Chinese bomberman.
Please make sure all the folders in the file with images or music (character, music, prop, etc.), effects(place, bomb, shieldbreak, etc.) and images that are outside (maps.txt, arrow1.png, arrow2.png, keys.png, music.png, no music.png, no volume.png, volume.png) are present when you run!

———————————————————————
There are 4 main classes:
Main - Majority of the code and game functions with various check check collision methods for different things like bomb, bomb range, walls, as well as props. This class itself is a panel!
Character - All the info about character like power, number of bombs to be placed, scores, etc!
Map - All the info about the map like the original maps, blocks that are destructible, where props summon
Props - All the info about the props in the game like their image, bomb+1, shield and various functions that occurs when a character collides with a prop during the game.
————————————————————————
Functionalities Missing from my original plan:
In my original plan, I guaranteed the functionalities for: life+1, walk faster, bigger bomb power, shield, bomb+1, mine bombs, and confuse enemy/gas.
Luckily I completely all of these functionalities but I didn't get the time to do more.
If I had more time, the next one for me to work on would probably be rocket and push bomb.
————————————————————————–
Additional functionalities added from your original plan:
Unfortunately none :(
————————————————————————–
Known bugs/errors:
1. Player 1 can push player 2, I still haven't figured out how to prevent the pushing for player 1 since player 2 can't push player 1 :|
2. If a series of prop setting (i.e. mine/gas) happens too quickly, it may crash (so I had a catch Exception e for that, I know it's generally not allowed but I just want it to return to the main page so everything flows and you may restart since it's a very low chance for it to happen)
3. Not really a bug but the hitbox for characters are a little bit bigger than they seem. Note that some characters (especially when they are facing different directions) are not CENTRED in the png file, therefore the hitbox could be closer to his/her left and right side. Overall, imagine your character as a square or rectangle when playing :)
4. Potentially a bug (noticed this afternoon, tried something not sure if it's fully fixed) when a bomb is placed, it is designed so that all the bomb within it's damage range will explode ALONG with it, sometimes a bomb not within the damage range could explode ALONG with it as well.
5. Not really a bug but if you press WASD (more than 1 of those) at the same time the direction of the character facing might not be the direction that the character is moving
————————————————————————–
Final words:
The sound effects were very last minute (started at 1 am so…) some of them sound pretty bad so bear with me ;)
It was such an amazing experience trying to code this.
I would have never thought that I am able to accomplish a project like this one simply by applying what I learned in class this year. A lot of the functionalities seem hard at first but gradually worked.

Thank you Ms. Wong :)

Kevin
Jun 23 2022 02:31