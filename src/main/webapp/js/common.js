var sidebar = $('#sidebar');
var apiFrameContainer = $('#api-frame-container');
var apiFrame = $('#api-frame');
var sidebarList = $('#sidebar-list');
var sidebarShow = $('#sidebar-show');
var SIDEBAR_STATE_COOKIE = "sidebar-state";
var SHOW_STATE = "show";
var HIDE_STATE = "hide";
var EXPANDED_CLASS = "expanded";

/**
 * Adjusts the size of the iframe and sidebar based on the state of the sidebar cookie
 */
function adjustSize() {
    if(apiFrame) {
        apiFrame.height(window.innerHeight);
        if($.cookie(SIDEBAR_STATE_COOKIE) && $.cookie(SIDEBAR_STATE_COOKIE) == HIDE_STATE) {
            sidebar.toggle();
            sidebarList.toggle();
            sidebarShow.toggle();
            apiFrameContainer.width(window.innerWidth * .92);
            apiFrameContainer.addClass('expanded');
        }
    }
}

/**
 * Toggles the sidebar to either show or hide using jquery animations
 */
function toggleSidebar() {
    sidebar.animate({width: 'toggle'});
    sidebarList.toggle();
    if(apiFrameContainer.hasClass(EXPANDED_CLASS)) {
        apiFrameContainer.removeClass(EXPANDED_CLASS);
        sidebarShow.toggle();
        apiFrameContainer.animate({width: window.innerWidth * .70});
    } else {
        apiFrameContainer.addClass(EXPANDED_CLASS);
        apiFrameContainer.animate({width: window.innerWidth * .92}, function() {
            sidebarShow.toggle();
        });
    }
    toggleSidebarCookie();
}

/**
 * Sets the sidebar cookie to either show or hide, based on what was last pressed
 */
function toggleSidebarCookie() {
    if(!$.cookie(SIDEBAR_STATE_COOKIE) || $.cookie(SIDEBAR_STATE_COOKIE) == SHOW_STATE) {
        $.cookie(SIDEBAR_STATE_COOKIE, HIDE_STATE, {
            expires: 10000,
            path: "/"
        });
    } else {
        $.cookie(SIDEBAR_STATE_COOKIE, SHOW_STATE, {
            expires: 10000,
            path: "/"
        });
    }
}