/*
    Copyright (c) 2011, DEVI S.A.S and its contributors.

    See http://github.com/deviSAS/j2me-client/wiki/Contributors
    for a full list of copyright holders.

    Redistribution and use in source and binary forms,
    with or without modification, are permitted provided
    that the following conditions are met:

    - Redistributions of source code must retain the above
      copyright notice, this list of conditions and the following
      disclaimer.

    - Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimer in the documentation and/or other materials
      provided with the distribution.

    - Neither the name of the <DEVI S.A.S> nor the names of its
      contributors may be used to endorse or promote products
      derived from this software without specific prior written
      permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
    FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
    COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
    BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
    CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
    LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
    ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
    POSSIBILITY OF SUCH DAMAGE.

 */

package com.devi.os.util;

public class Language {
    public static String[] Nombres  = new String[] {"Chat", "Amigos", "Grupos", "Inventario", "Mapa"};
    public static String[] Titles   = new String[] {"Iniciar Sesión", "Registro", "Error", "Mensaje del Día", "Advertencia", "Chat Local", "Sesiones de Chat", "Avatares cerca"};
    public static String[] Fields   = new String[] {"Nombre:", "Apellido:", "Contraseña:", "Nombre real:", "Apellido real:", "Email:"};
    public static String[] Buttons  = new String[] {"Entrar", "Registrar", "Salir", "Regresar", "Crear Cuenta", "Atras", "Menú", "Enviar"};
    public static String[] Labels   = new String[] {"Al registrar la cuenta, usted acepta los términos de servicio para la plataforma deviOS, si no conoce dichos términos por favor no continúe con el registro.",
                                                    "Esta aplicación utiliza una gran cantidad de paquetes de datos que pueden generar cobro en su cuenta móvil. Le recomendamos usar un plan de datos ilimitado ó red WiFi.",
                                                    "No hay mensajes de chat.",
                                                    "No se pudo desconectar de la región, intente salir más tarde"};

    public static String[] ItemsMenu = new String[] {"Refrescar", "Info", "Salir"};
    public static String[] AvatarNearMenu = new String[] {"IM", "Perfil", "Teleportar"};
    public static String[] OfflineFriendMenu = new String[] {"IM", "Perfil"};


    public static String[] Tab = new String[] {"En línea", "Desconectado"};
    public static String[] Loading = new String[] { "Conectando..", "Desconectando..", "Buscando avatares..", "Enviando mensaje..", "Cargando Amigos.."};

    public static String DialogDefault = "Aceptar";
    public static String OfflineMessage = "(guardado) ";
    public static String GroupChat = "Chat de grupo";
    public static String LocationAt = "Usted está en ";
    public static String NoIM = "No hay mensajes instantáneos";
    public static String NoAvArea = "No hay avatares en el área";
    
    public static String NoFriends = "No hay amigos ";
    public static String OnlineF = "en línea";
    public static String OfflineF = "desconectados";
}
