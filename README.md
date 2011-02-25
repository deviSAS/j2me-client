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

Sponsor a module or just donate to the development, use the following button, you can contact us at <devi@foravatars.com>.

<form action="https://www.paypal.com/cgi-bin/webscr" method="post">
<input type="hidden" name="cmd" value="_s-xclick">
<input type="hidden" name="encrypted" value="-----BEGIN PKCS7-----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-----END PKCS7-----
">
<input type="image" src="https://www.paypal.com/en_US/i/btn/btn_donate_SM.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
<img alt="" border="0" src="https://www.paypal.com/es_XC/i/scr/pixel.gif" width="1" height="1">
</form>

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
> - AvatarsNear: Receives and parse 'Avatars nears' data when requested, this that is sent to a UI file.
> - ChatLocal: Receives local chat messages, holds up to 10 messsages in reverse order (to save memory).
> - DataSend: Handles the system data transfer, takes care of the format and sends the data to the server.
> - Events: Receive events data from server and transfer them to the correct class.
> - Friends: Handles avatar friends, loaded on start up, holds friend status (on/off) and UUD.
> - IMSessions: Handles each IM session (even offline messages) by an unique ID.
> - MainUI: Handles the whole system User Interface, decides wich UI file call.
> - UserLogin:  Makes possible the user login parsing the login view data and sending it to DataSend.
> - deviOS: The core of the system, actually only starts everything then is managed by MainUI.

> com.devi.os.json/
> - JSONClasses

> com.devi.os.ui/
> com.devi.os.util