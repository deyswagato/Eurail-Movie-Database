$("#searchMovie").click(function(e) {
    var href = new URL(window.location.href);
    href.searchParams.set('searchText', $("#searchText").val());
    window.location = href.toString();
});