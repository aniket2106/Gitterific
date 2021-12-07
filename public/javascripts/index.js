(function() {
    var parseRepos;
    console.log("Entered js file");
    console.log("abcbabc");
    var ws;
    console.log("Waiting for WebSocket");
    ws = new WebSocket($("body").data("ws-url"));
    ws.onmessage = function (event) {
        var message;
        message = JSON.parse(event.data);
        console.log(message);
        if (message.items) {
            parseData(message);
        }
    };

    parseData = (data) => {
        var query = data.query.replace(/ /g, '');
        repoListQuery = $("#repo-"+query);
        tableNode = document.querySelector("#repo-"+query);
        if (repoListQuery.length === 0) {
            var temp="";
            temp = '<div><p>Top 10 search results for: ' + data.query + '<table id="repo-' + query + '"><tr><th>Author</th><th>Repo name</th><th>Topics</th></tr>';
            temp += getRows(data.items);
            temp += '</table>';
            $("#repos").prepend(temp);

        } else {
            var temp = "";
            
            temp += '<tr><th>Author</th><th>Repo name</th><th>Topics</th></tr>';
            temp += getRows(data.items);
            document.querySelector("#repo-"+query).innerHTML = temp;
        }
    };

    getRows = (dataArray) => {
        var temp = "";
        dataArray.forEach((x, i) => {

            var topics = "";

            x.topics.forEach((topic, key) => {
                topics += '<a href="/search/topic/' + topic + '" target="_blank">' + topic + '</a>';
            });

            temp += '<tr id="repochild-' + query + '"><td><a href="/' + x.owner.login + '" target="_blank">' + x.owner.login + '</a></td>' +
                '<td><a href="/repo/' + x.owner.login + '/' + x.name + '" target="_blank">' + x.owner.login + '</a></td>' +
                '<td><span>' + topics + '</span></td></tr>';
        });
        return temp;
    };

    return $("#searchForm").submit(function (event) {
        event.preventDefault();
        if ($("#query").val() !== '') {
            console.log("Sending WS with value " + $("#query").val());
            ws.send(JSON.stringify({
                query: $("#query").val()
            }));
            return $("#query").val("");
        }
    });

}).call(this);