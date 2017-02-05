/**
 * Forked from https://github.com/Toliver182/SmartThings-Kodi who had
 * forked from a plex version: https://github.com/iBeech/SmartThings/tree/master/PlexManager
 *
 *  I added some stuff like 'shutdown' so you can tell kodi to shutdown (the idea being it can turn off your TV)
 *  Also added better tracking of whats playing, I want to control the lights differently so I added some customer attributes
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
//User customisation - update this method with your own preferences if you want.
//I will keep the preferences in order, so you can copy and past over them
//NB you do not need to update this and the prefs are overwritten by device handler prefs if you update there
def getDefaultTheme(){
    def userDefaultThemeMap = [:]
    //v1.2 START
    //ICONS
    userDefaultThemeMap.themeName = "Default"
    userDefaultThemeMap.iconStop = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/stop-icon.png"
    userDefaultThemeMap.iconShutdown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/power-icon.png"
    userDefaultThemeMap.iconUp = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/up-icon.png"
    userDefaultThemeMap.iconDown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/down-icon.png"
    userDefaultThemeMap.iconLeft = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/left-icon.png"
    userDefaultThemeMap.iconRight = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/right-icon.png"
    userDefaultThemeMap.iconBack = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/back-icon.png"
    userDefaultThemeMap.iconInfo = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/info-icon.png"
    userDefaultThemeMap.iconSkipFwd = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/small-fwd-icon.png"
    userDefaultThemeMap.iconSkipRwd = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/small-rwd-icon.png"
    userDefaultThemeMap.iconNext = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/next-icon.png"
    userDefaultThemeMap.iconPrevious = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/prev-icon.png"
    userDefaultThemeMap.iconMenu = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/menu-icon.png"
    userDefaultThemeMap.iconHome = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/home-icon.png"
    userDefaultThemeMap.iconPgUp = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/pg-up-icon.png"
    userDefaultThemeMap.iconPgDown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/pg-down-icon.png"
    //DECORATION
    userDefaultThemeMap.decStop = "ring"
    userDefaultThemeMap.decShutdown = "flat"
    userDefaultThemeMap.decUp = "flat"
    userDefaultThemeMap.decDown = "flat"
    userDefaultThemeMap.decLeft = "flat"
    userDefaultThemeMap.decRight = "flat"
    userDefaultThemeMap.decBack = "flat"
    userDefaultThemeMap.decInfo = "ring"
    userDefaultThemeMap.decSkipF = "flat"
    userDefaultThemeMap.decSkipB = "flat"
    userDefaultThemeMap.decNext = "flat"
    userDefaultThemeMap.decPrev = "flat"
    userDefaultThemeMap.decMenu = "flat"
    userDefaultThemeMap.decHome = "flat"
    userDefaultThemeMap.decPup = "flat"
    userDefaultThemeMap.decPdown = "flat"
    //COLOURS
    userDefaultThemeMap.colMainWaiting = "#ffffff"     //White
    userDefaultThemeMap.colMainStartup = "#90d2a7"     //Light Green
    userDefaultThemeMap.colMainPlaying = "#79b821"     //Green
    userDefaultThemeMap.colMainStopped = "#153591"     //Blue
    userDefaultThemeMap.colMainPaused = "#e86d13"      //Orange
    userDefaultThemeMap.colMainShutdown = "#e84e4e"    //Red
    //v1.2 END
    //v1.3 START
    //v1.3 END
    //Return
    return userDefaultThemeMap
}

def getUserPref(pref){
    def prefsMap = [:]
    //Main Icon
    prefsMap.iconMain = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/main-icon.png"
    //Select Colour
    prefsMap.colSelectActive = "#22a3ec"    //Blue
    prefsMap.colSelectInactive = "#ffffff"  //White
    //Select decoration
    prefsMap.decPush = "ring"
    //CATEGORY SETTINGS
    prefsMap.movieLabels = "cinema, movie, film"
    prefsMap.sportLabels = "sport"
    prefsMap.tvLabels = "bbc, itv, channel, sky, amc, fox"
    prefsMap.minMovieRuntime = 4200
    return prefsMap[pref]
}

metadata {
    definition (name: "Kodi-Client", namespace: "north3221", author: "north3221") {
        capability "musicPlayer"        //For playback etc
        capability "mediaController"    //Used for handling the action requests
        capability "Momentary"          //Added for 'push' command I use for 'select' on kodi
        //Custom Commands
        command "describeAttributes"
        command "executeAction" , ["string"]
        command "shutdown"
        command "up"
        command "down"
        command "left"
        command "right"
        command "back"
        command "info"
        command "fastforward"
        command "rewind"
        command "skipforward"
        command "skipbackward"
        command "pageUp"
        command "pageDown"
        command "menu"
        command "home"

        //Custom attributes
        attribute "currentPlayingType", "string"
        attribute "currentPlayingCategory", "enum", ["Movie", "TV Show", "Sports", "None", "Unknown"]
        attribute "currentPlayingName", "string"
    }

    tiles(scale: 2) {
        valueTile("main", "device.status", width: 6, height: 2, canChangeIcon: false) {
            state "waiting", label:'Waiting', action:"push" ,icon:"${getUserPref('iconMain')}", backgroundColor:"${getUserPref('colMainWaiting')}", defaultState: true
            state "startup", label:'Startup', action:"push" ,icon:"${getUserPref('iconMain')}", backgroundColor:"${getUserPref('colMainStartup')}", nextState: "waiting"
            state "playing", label:'Playing', action:"pause", icon:"${getUserPref('iconMain')}", backgroundColor:"${getUserPref('colMainPlaying')}", nextState: "waiting"
            state "stopped", label:'Stopped', action:"push", icon:"${getUserPref('iconMain')}", backgroundColor:"${getUserPref('colMainStopped')}", nextState: "waiting"
            state "paused", label:'Paused', action:"play", icon:"${getUserPref('iconMain')}", backgroundColor:"${getUserPref('colMainPaused')}", nextState: "waiting"
            state "shutdown", label:'Shutdown', action:"push", icon:"${getUserPref('iconMain')}", backgroundColor:"${getUserPref('colMainShutdown')}", nextState: "waiting"
        }

        multiAttributeTile(name: "mediaMulti", type:"mediaPlayer", width:6, height:4) {
            tileAttribute("device.status", key: "PRIMARY_CONTROL") {
                attributeState("paused", label:"Paused")
                attributeState("playing", label:"Playing")
                attributeState("stopped", label:"Stopped")
            }
            tileAttribute("device.status", key: "MEDIA_STATUS") {
                attributeState("paused", label:"Paused", action:"play", nextState: "waiting", defaultState: true)
                attributeState("playing", label:"Playing", action:"play", nextState: "waiting")
                attributeState("stopped", label:"Stopped", action:"play", nextState: "waiting")
            }
            tileAttribute("device.status", key: "PREVIOUS_TRACK") {
                attributeState("status", action:"rewind", defaultState: true)
            }
            tileAttribute("device.status", key: "NEXT_TRACK") {
                attributeState("status", action:"fastforward", defaultState: true)
            }
            tileAttribute ("device.level", key: "SLIDER_CONTROL") {
                attributeState("level", action:"music Player.setLevel")
            }
            tileAttribute ("device.mute", key: "MEDIA_MUTED") {
                attributeState("unmuted", action:"music Player.mute", nextState: "muted")
                attributeState("muted", action:"music Player.mute", nextState: "unmuted")
            }
            tileAttribute("device.trackDescription", key: "MARQUEE") {
                attributeState("trackDescription", label:'${currentValue}', defaultState: true)
            }
        }

        standardTile("stop", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decStop')}") {
            state "default", label:'', action:"music Player.stop", icon:"${getUserTheme('default','iconStop')}", defaultState: true
            state "glyphs", label:'', action:"music Player.stop", icon:"${getUserTheme('glyphs','iconStop')}"
        }

        standardTile("shutdown", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decShutdown')}") {
            state "default", label:'', action:"shutdown", icon:"${getUserTheme('default','iconShutdown')}", defaultState: true
            state "glyphs", label:'', action:"shutdown", icon:"${getUserTheme('glyphs','iconShutdown')}"
        }

        standardTile("up", "state.theme", width: 2, height: 1, decoration: "${getUserTheme('decUp')}") {
            state "default", label:'', action:"up", icon:"${getUserTheme('default','iconUp')}", defaultState: true
            state "glyphs", label:'', action:"up", icon:"${getUserTheme('glyphs','iconUp')}"
        }

        standardTile("down", "state.theme", width: 2, height: 1, decoration: "${getUserTheme('decDown')}") {
            state "default", label:'', action:"down", icon:"${getUserTheme('default','iconDown')}", defaultState: true
            state "glyphs", label:'', action:"down", icon:"${getUserTheme('glyphs','iconDown')}"
        }

        standardTile("left", "state.theme", width: 1, height: 2, decoration: "${getUserTheme('decLeft')}") {
            state "default", label:'', action:"left", icon:"${getUserTheme('default','iconLeft')}", defaultState: true
            state "glyphs", label:'', action:"left", icon:"${getUserTheme('glyphs','iconLeft')}"
        }

        standardTile("right", "state.theme", width: 1, height: 2, decoration: "${getUserTheme('decRight')}") {
            state "default", label:'', action:"right", icon:"${getUserTheme('default','iconRight')}", defaultState: true
            state "glyphs", label:'', action:"right", icon:"${getUserTheme('glyphs','iconRight')}"
        }

        standardTile("push", "device.status", width: 2, height: 2, decoration: "${getUserPref('decPush')}") {
            state "stopped", label:'Select', action:"push", backgroundColor:"${getUserPref('colSelectActive')}", defaultState: true
            state "playing", label:'Select', action:"push", backgroundColor:"${getUserPref('colSelectInactive')}"
            state "paused", label:'Select', action:"push", backgroundColor:"${getUserPref('colSelectInactive')}"
        }

        standardTile("back", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decBack')}") {
            state "default", label:'', action:"back", icon:"${getUserTheme('default','iconBack')}", defaultState: true
            state "glyphs", label:'', action:"back", icon:"${getUserTheme('glyphs','iconBack')}"
        }

        standardTile("info", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decInfo')}") {
            state "default", label:'', action:"info", icon:"${getUserTheme('default','iconInfo')}", defaultState: true
            state "glyphs", label:'', action:"info", icon:"${getUserTheme('glyphs','iconInfo')}"
        }

        standardTile("skipforward", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decSkipF')}") {
            state "default", label:'', action:"skipforward", icon:"${getUserTheme('default','iconSkipFwd')}", defaultState: true
            state "glyphs", label:'', action:"skipforward", icon:"${getUserTheme('glyphs','iconSkipFwd')}"
        }

        standardTile("skipbackward", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decSkipB')}") {
            state "default", label:'', action:"skipbackward", icon:"${getUserTheme('default','iconSkipRwd')}", defaultState: true
            state "glyphs", label:'', action:"skipbackward", icon:"${getUserTheme('glyphs','iconSkipRwd')}"
        }

        standardTile("next", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decNext')}") {
            state "default", label:'', action:"nextTrack", icon:"${getUserTheme('default','iconNext')}", defaultState: true
            state "glyphs", label:'', action:"nextTrack", icon:"${getUserTheme('glyphs','iconNext')}"
        }

        standardTile("previous", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decPrev')}") {
            state "default", label:'', action:"previousTrack", icon:"${getUserTheme('default','iconPrevious')}", defaultState: true
            state "default", label:'', action:"previousTrack", icon:"${getUserTheme('glyphs','iconPrevious')}"
        }

        standardTile("menu", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decMenu')}") {
            state "default", label:'', action:"menu", icon:"${getUserTheme('default', 'iconMenu')}", defaultState: true
            state "glyphs", label:'', action:"menu", icon:"${getUserTheme('glyphs', 'iconMenu')}"
        }

        standardTile("home", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decHome')}") {
            state "default", label:'', action:"home", icon:"${getUserTheme('default','iconHome')}", defaultState: true
            state "glyphs", label:'', action:"home", icon:"${getUserTheme('glyphs','iconHome')}"
        }

        standardTile("pgUp", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decPup')}") {
            state "pgUp", label:'', action:"pageUp", icon:"${getUserTheme('iconPgUp')}", defaultState: true
        }
        standardTile("pgDown", "state.theme", width: 1, height: 1, decoration: "${getUserTheme('decPdown')}") {
            state "default", label:'', action:"pageDown", icon:"${getUserTheme('default','iconPgDown')}", defaultState: true
            state "glyphs", label:'', action:"pageDown", icon:"${getUserTheme('glyphs','iconPgDown')}"
        }

        main("main")
        details(["mediaMulti",
                 "previous", "skipbackward", "up", "skipforward", "next",
                 "info", "left", "push", "right", "pgUp",
                 "menu", "pgDown",
                 "shutdown", "stop", "down", "home","back"
        ])
    }

    preferences {
        input "inputMovieLabel", "text", required: false, title: "Movie labels: search kodi label for:", defaultValue: "${getUserPref('movieLabels')}", displayDuringSetup: false
        input "inputSportLabel", "text", required: false, title: "Sport labels: search kodi label for:", defaultValue: "${getUserPref('sportLabels')}", displayDuringSetup: false
        input "inputTVLabel", "text", required: false, title: "TV labels: search kodi label for:", defaultValue: "${getUserPref('tvLabels')}", displayDuringSetup: false
        input "inputMinMovieRuntime", "number", required: false, title: "Min Runtime to class as Movie (secs):", defaultValue: "${getUserPref('minMovieRuntime')}", displayDuringSetup: false
        input "inputShutdownAsQuit", "bool", required: false, title: "Shutdown as Quit:", defaultValue: false, displayDuringSetup: false
        input "inputBigSkip", "bool", required: false, title: "Big Skip: Big (10m) Small (30s)", defaultValue: false, displayDuringSetup: false
        input name: "inputTheme", type: "enum", options:["default", "glyphs"], description: "Select a theme for the device handler", required: false, title: "Theme", defaultValue: "Default", displayDuringSetup: false
    }
}

def installed() {
    log.debug "Installed"
    state.theme = inputTheme ?: "default"
}

def initialize() {
    log.debug "Initialised"
    state.defaultTheme = defaultTheme
    state.glyphsTheme = glyphsTheme

    if ((inputTheme != null) && (state.theme != inputTheme)){
        state.theme = inputTheme
        //sendEvent(name: "theme", value: inputTheme)
    }
}

def updated() {
    log.debug "Updated"
    log.debug "Input quotes = " + "$inputTheme"
    log.debug "Input noquote= " + inputTheme
    initialize()
}

// parse events into attributes
def parse(evt) {
    log.debug "Event :" + evt.value

    def msg = parseLanMessage(evt);

    if(msg.header){
        if(msg.header.contains("Unauthorized")){
            log.debug "Cannot authenticate: Please check kodi username and password"
            return
        }
    }

    if (!msg.body){
        return
    }

    if( msg.body == "{\"id\":1,\"jsonrpc\":\"2.0\",\"result\":\"OK\"}"){
        log.debug "received ok"
        return
    }

    if( msg.body == "{\"id\":1,\"jsonrpc\":\"2.0\",\"result\":{\"speed\":0}}"){
        log.debug "received speed 0"
        return
    }
    if( msg.body == "{\"id\":1,\"jsonrpc\":\"2.0\",\"result\":{\"speed\":1}}"){
        log.debug "received speed 1"
        return
    }
    if (msg.body == "{\"error\":{\"code\":-32100,\"message\":\"Failed to execute method.\"},\"id\":1,\"jsonrpc\":\"2.0\"}")
    {
        log.debug "error returned from kodi"
        return
    }

    if (msg.body.startsWith("{\"id\":\"VideoGetItem\"")) {
        parseNowPlaying(msg.body)
    }

}

def parseNowPlaying(msgBody){
    log.info "Parsing Now Playing"
    //Lists to check 'type' against to set category - I think this is the best way to validate type as this means kodi knows the type
    def tvShowType = ["episode"]
    def movieType = ["movie"]

    //start
    def slurper = new groovy.json.JsonSlurper().parseText(msgBody)
    def type = slurper.result.item.type
    def title = slurper.result.item.title

    //initialise category - Unknown so if not set stays as Unknown
    def category = "Unknown"
    def playingTitle = ""

    //If kodi doesnt know then let me try and work it out - else use what kodi says
    if (type == "unknown"){
        def label = slurper.result.item.label
        def runtime = slurper.result.item.runtime
        def plot = slurper.result.item.plot

        log.info "unknown type so checking label (" + label + ") contains Movie " + movieLabels + " or Sport " + sportLabels + " or TV Show " + tvLabels + " if not will check runtime and finally will check plot"
        //Check labels
        if(movieLabels.any {label.toLowerCase().contains(it)}){
            category = "Movie"
            log.info "Matched " + category
        }else if(sportLabels.any {label.toLowerCase().contains(it)}) {
            category = "Sports"
            log.info "Matched " + category
        }else if(tvLabels.any {label.toLowerCase().contains(it)}) {
            category = "TV Show"
            log.info "Matched " + category
        }else if (runtime >= minMovieRuntime){
            category = "Movie"
            log.info "Runtime (" + runtime + ") is greater than (" + minMovieRuntime + ") so category set to (" + category + ")"
        }else if (runtime > 0){
            category = "TV Show"
            log.info "Runtime (" + runtime + ") is greater than (0) but less than movie min runtime (" + minMovieRuntime + ") so category set to (" + category + ")"
        }else if (plot.length() > 0){
            category = "Movie"
            log.info "Plot (" + plot + ") exists so category set to (" + category + ")"
        }
        playingTitle = label
    } else if (movieType.any {type.toLowerCase().contains(it)}){
        category = "Movie"
        log.info "Type is (" + type + ") so category set to (" + category + ")"
        playingTitle = title
    } else if (tvShowType.any {type.toLowerCase().contains(it)}){
        def showTitle = slurper.result.item.showtitle
        category = "TV Show"
        log.info "Type is (" + type + ") so category set to (" + category + ")"
        playingTitle = showTitle + " : " + title
    }

    setPlaybackTitle(type, category, playingTitle)
    log.info "Current Playing type (" + type + ") category (" + category + ") title (" + playingTitle + ")"
}

def executeAction(action) {
    log.debug "Execute Action Request = " + action
    sendEvent(name: "currentActivity", value: device.deviceNetworkId + "." + action);
    //Need to reset the command as hib wont accept duplicates
    sendEvent(name: "currentActivity", value: "RESETACTION");
}

def push() {
    executeAction("select")
}
//Play pause for action button
def play() {
    executeAction("playpause")
}

def stop() {
    executeAction("stop")
}

def shutdown() {
    executeAction(inputShutdownAsQuit ? "quit" : "shutdown")
}

def fastforward(){
    executeAction("fastforward")
}

def rewind(){
    executeAction("rewind")
}

def skipbackward(){
    executeAction(inputBigSkip ? "skip.bigbackward" : "skip.smallbackward")
}

def skipforward(){
    executeAction(inputBigSkip ? "skip.bigforward" : "skip.smallforward")
}

def up(){
    executeAction("up")
}

def down(){
    executeAction("down")
}

def left(){
    executeAction("left")
}

def right(){
    executeAction("right")
}

def back(){
    executeAction("back")
}

def info(){
    executeAction("info")
}

def mute(){
    executeAction("mute")
}

def nextTrack(){
    executeAction("skipnext")
}

def previousTrack(){
    executeAction("skipprevious")
}

def pageUp(){
    executeAction("pageup")
}

def pageDown(){
    executeAction("pagedown")
}

def menu(){
    executeAction("contextmenu")
}

def home(){
    executeAction("home")
}


def setLevel(level) {
    sendEvent(name: "level", value: level);
    executeAction("setVolume." + level);
}

def setPlaybackState(state) {
    log.debug "Setting playback state to: " + state
    switch(state) {
        case "stopped":
            sendEvent(name: "status", value: "stopped");
            setPlaybackTitle("","","")
            break;
        case "playing":
            sendEvent(name: "status", value: "playing");
            break;
        case "paused":
            sendEvent(name: "status", value: "paused");
            break;
        case "shutdown":
            sendEvent(name: "status", value: "shutdown");
            setPlaybackTitle("","", "")
            break;
        case "startup":
            sendEvent(name: "status", value: "startup");
            setPlaybackTitle("","", "")
            break;
    }
}

def setPlaybackTitle(type, category, name) {
    def track = ""

    if(type == ""){
        type = 'None'
    } else {
        track = "Kodi Type: " + type
    }
    if(category == ""){
        category = 'None'
    } else {
        if (track != ""){
            track = track + "\n"
        }
        track = track + "Category: " + category
    }
    if (track != ""){
        track = track + "\n"
    }
    track = track + name
    if(name == ""){
        name = 'Nothing Playing'
        track = name
    }

    log.debug "Setting title to :" + name
    log.debug "Track = " + track
    sendEvent(name: "currentPlayingType", value: type)
    sendEvent(name: "currentPlayingCategory", value: category)
    sendEvent(name: "currentPlayingName", value: name)
    sendEvent(name: "trackDescription", value: track)
}

//TOOLS
//define attributes for CoRE
def describeAttributes(payload) {
    payload.attributes = [
            [ name: "currentPlayingType", type: "string"],
            [ name: "currentPlayingCategory", type: "enum", options: ["Movie", "TV Show", "Sports", "None", "Unknown"]],
            [ name: "currentPlayingName", type: "string"]
    ]
    return null
}
//Getters
def getMovieLabels() {
    return (inputMovieLabel ?: getUserPref("movieLabels")).replaceAll("\\s","").toLowerCase().split(',').toList()
}
def getSportLabels() {
    return (inputSportsLabel ?: getUserPref("sportLabels")).replaceAll("\\s","").toLowerCase().split(',').toList()
}
def getTvLabels() {
    return (inputTVLabel ?: getUserPref("tvLabels")).replaceAll("\\s","").toLowerCase().split(',').toList()
}
def getMinMovieRuntime(){
    return inputMinMovieRuntime ?: getUserPref("minMovieRuntime")
}

//Themes
def getUserTheme(theme, index){

    switch (theme){
        case "glyphs":
            if (!state.glyphsTheme){
                state.glyphsTheme = glyphsTheme
            }
            return state.glyphsTheme[index]
        break;
        default:
            if (!state.defaultTheme){
                state.defaultTheme = defaultTheme
            }
            return state.defaultTheme[index]
    }
}


def getGlyphsTheme(){
    def userGlyphsThemeMap = [:]
    //ICONS
    userGlyphsThemeMap.themeName = "Glymphs"
    userGlyphsThemeMap.iconMain = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/main-icon.png"
    userGlyphsThemeMap.iconStop = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/stop-icon.png"
    userGlyphsThemeMap.iconShutdown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/power-icon.png"
    userGlyphsThemeMap.iconUp = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/up-icon.png"
    userGlyphsThemeMap.iconDown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/down-icon.png"
    userGlyphsThemeMap.iconLeft = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/left-icon.png"
    userGlyphsThemeMap.iconRight = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/right-icon.png"
    userGlyphsThemeMap.iconBack = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/back-icon.png"
    userGlyphsThemeMap.iconInfo = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/info-icon.png"
    userGlyphsThemeMap.iconSkipFwd = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/small-fwd-icon.png"
    userGlyphsThemeMap.iconSkipRwd = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/small-rwd-icon.png"
    userGlyphsThemeMap.iconNext = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/next-icon.png"
    userGlyphsThemeMap.iconPrevious = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/prev-icon.png"
    userGlyphsThemeMap.iconMenu = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/menu-icon.png"
    userGlyphsThemeMap.iconHome = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/home-icon.png"
    userGlyphsThemeMap.iconPgUp = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/pg-up-icon.png"
    userGlyphsThemeMap.iconPgDown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/glyphs/pg-down-icon.png"
    //DECORATION
    userGlyphsThemeMap.decStop = "flat"
    userGlyphsThemeMap.decShutdown = "flat"
    userGlyphsThemeMap.decUp = "flat"
    userGlyphsThemeMap.decDown = "flat"
    userGlyphsThemeMap.decLeft = "flat"
    userGlyphsThemeMap.decRight = "flat"
    userGlyphsThemeMap.decBack = "flat"
    userGlyphsThemeMap.decInfo = "flat"
    userGlyphsThemeMap.decSkipF = "flat"
    userGlyphsThemeMap.decSkipB = "flat"
    userGlyphsThemeMap.decNext = "flat"
    userGlyphsThemeMap.decPrev = "flat"
    userGlyphsThemeMap.decMenu = "flat"
    userGlyphsThemeMap.decHome = "flat"
    userGlyphsThemeMap.decPup = "flat"
    userGlyphsThemeMap.decPdown = "flat"
    //COLOURS
    userGlyphsThemeMap.colMainWaiting = "#ffffff"     //White
    userGlyphsThemeMap.colMainStartup = "#90d2a7"     //Light Green
    userGlyphsThemeMap.colMainPlaying = "#79b821"     //Green
    userGlyphsThemeMap.colMainStopped = "#153591"     //Blue
    userGlyphsThemeMap.colMainPaused = "#e86d13"      //Orange
    userGlyphsThemeMap.colMainShutdown = "#e84e4e"    //Red
    return userGlyphsThemeMap
}