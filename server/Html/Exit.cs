
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using MiniHttpd;
using OpenMetaverse;

namespace deviMobile.Html
{
    public class Exit : IFile
    {
        // Fields
        private string contenttype = "text/pain; charset=utf-8";
        private string name;
        private IDirectory parent;
        private Dictionary<Guid, User> users;

        // Methods
        public Exit(string name, IDirectory parent, Dictionary<Guid, User> users)
        {
            this.name = name;
            this.parent = parent;
            this.users = users;
        }


        public void Dispose()
        {
        }

        public void OnFileRequested(HttpRequest request, IDirectory directory)
        {
            request.Response.ResponseContent = new MemoryStream();
            StreamWriter writer = new StreamWriter(request.Response.ResponseContent);

            Console.WriteLine("[EXIT] Disconnecting users..");

            System.Threading.Thread.Sleep(1000);

            foreach (KeyValuePair<Guid, User> entry in users)
            {
                try {
                    User user = entry.Value;
                    //Guid session = (Guid)entry.Key;
                    if (user.Events != null)
                    {
                        user.Events.Network_Disconnected(this, new DisconnectedEventArgs(NetworkManager.DisconnectType.ServerInitiated, "Your deviMobile session has timed out."));
                        Console.WriteLine("[EXIT] Transmitted logout alert to " + user.Client.Self.FirstName + " " + user.Client.Self.LastName + ". Waiting...");
                        System.Threading.Thread.Sleep(1000);
                        user.Events.deactivate();
                    }
                    Console.WriteLine("[EXIT] Disconnecting " + user.Client.Self.FirstName + " " + user.Client.Self.LastName + "...");
                    user.Client.Network.Logout();
                } catch(Exception e) {
                    Console.WriteLine("[ERROR] " + e.Message);
                }
            }

            System.Threading.Thread.Sleep(1000);
            Console.WriteLine("[EXIT] Done.");
            Environment.Exit(0);
        }

        // Properties
        public string ContentType
        {
            get
            {
                return this.contenttype;
            }
        }

        public string Name
        {
            get
            {
                return this.name;
            }
        }

        public IDirectory Parent
        {
            get
            {
                return this.parent;
            }
        }
    }
}