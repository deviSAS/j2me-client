#J2ME Client to access OpenSim based Services

##About J2ME Client

'j2me client' seeks to provide access through mobile devices (low and medium range) to OpenSimulator networks. These devices do not have a significant performance so web based viewers limits the possibilities as opposed to a Java application which runs on most devices natively (devices from 2006).

To connect the user with the networks, we modified the AjaxLife server code, reducing the traffic impact (by reducing packets, operations, security requests), so with the limited memory we have in J2ME MIDP1.1, we can still interact in real-time with the platform.

##Project Status

The project is quite immature, partly due to the projects we work at the moment which take too much of our time and the volume of work and support neeeded to bring the platform to as many devices as possible. 

You can help us develop the application and get it ready as soon as possible either by sponsoring the module that have greater importance to you (see below Goals) with a donation or by helping us to develop it.

What is finished and functional so far is:

- User authentication and login
- Grid selection (Region is easy to implement and will be added soon)
- Local chat with primitive's chat highlight and location
- Instant messaging (av2av)
- Avatars in the area (and ability to send IM)
- Online and offline friends listing *
- Internal UUID2Name storage
- Server side UUID2Name
- Touch interface for capable devices
- Multi-threading
- Safe logout
- View transitions (slides) and some fancy effects
- Submenu for easy functionality *
- Language module (easy to implement different languages)

Things marked with * needs more work.

##Goals:

The goals of the project and what is expected to arrive for the third quarter of 2011 is:

- User profiles (with images)
- World & local map (with user dots and drag&drop if possible)
- Teleport and simple movement interface
- Group chat / management
- User Inventory (able to transfer stuff)
- Notecards creation & edition
- Texture/Image view
- Full alert system (new chat, message of the day, friend login/out)
- Events dialogs (friend request, teleport lure) 


##Future functionality:

- Image upload
- Camera support (for image upload)
- Social network connection (Where I'm, What I'm doing)
- Network stats (data in/out, transfer velocity, dilation)

##Help the project

Sponsor a module or just donate to the development, click here to [DONATE](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=KCT7JAM37SWDU).
You can contact us at <devi@foravatars.com> if need help with the donation process, also let us know who you are! :)


## Specifications

- JSON for data transfer
- LWUIT interface with custom theme and icons
- AjaxLife modified server
- Module distrubuted services with separate threads

## Developing

Before you start, we recommend you to use the following:

- NetBeans IDE 6.9.1 with J2ME support / Java
- Notepad++
- Mysysgit (Git for windows)

### Setting up the project

Download the lastest Git, you will find the following folders

> source: contains the J2ME app source
> libs: contains used libs (LWUIT)
> demo: pre-compiled app demo

Start a new demo project by loading the folder inside source folder, make sure LWUIT.jar dependency is OK.

### Internal modules

Java class files distribution:

> com.devi.os/
> - AvatarsNear: Receives and parse 'Avatars nears' data when requested, then, the data is sent to an UI file.
> - ChatLocal: Receives local chat messages, holds up to 10 messsages in reverse order (to save memory).
> - DataSend: Handles the system data transfer, takes care of the format and sends the data to the server.
> - Events: Receive events data from server and transfer them to the correct class.
> - Friends: Handles avatar friends, loaded on log in, holds friend status (on/off) and UUID.
> - IMSessions: Handles each IM session (even offline messages) by an unique ID.
> - MainUI: Handles the whole system User Interface, decides wich UI file call.
> - UserLogin:  Makes possible the user login parsing the login view data and sending it to DataSend.
> - deviOS: The core of the system, actually only starts everything then is managed by MainUI.

> com.devi.os.json/
> - JSONClasses

> com.devi.os.ui/
> -
> com.devi.os.util