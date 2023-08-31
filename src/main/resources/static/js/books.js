let pageCounter = 0;

window.onscroll = function(ev) {
    if ((window.innerHeight + window.scrollY) >= document.body.scrollHeight) {
        loadBookTable(pageCounter);
    }
};

$(document).ready(function() {
    loadBookTable(pageCounter);

    $(document).on("click", ".save-book-btn", function(event) {
        event.preventDefault();
        var form = $(this).closest("form");
        saveBook(form);
    });

    $(document).on("keydown", "form div input", function(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            var form = $(this).closest("form");
            saveBook(form);
        }
    });
});

function saveBook(form) {
    var jsonData = {
        name: form.find("input[name='name']").val(),
        author: form.find("input[name='author']").val(),
        description: form.find("input[name='description']").val()
    }

    $.ajax({
        url: form.attr("action"),
        type: "POST",
        headers: {
            "X-CSRF-Token":form.find("input[name='_csrf']").val()
        },
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        success: function(data, status) {
            console.log(data);
            prepend(data);
        },
        error: function(data) {
            notify(data.responseJSON.message);
        }
    });
}


function loadBookTable(page) {
    $.ajax({
        url: "/books",
        type: "GET",
        data: jQuery.param({ page: pageCounter, size: "50", sort: "created,desc"}) ,
        success: function(data, status) {
            const bookTable = document.getElementById("book-table-body")
            fillBookTable(bookTable, data.content);
            pageCounter++;
        },
        error: function(data) {
            console.log(data);
            notify(data.responseJSON.message);
        }
    });

}

function prepend(book) {
    const bookTable = document.getElementById("book-table-body");
    let row = document.createElement("tr");
    row.innerHTML = `
        <td>${book.name}</td>
        <td>${book.author}</td>
        <td>${book.description}</td>
    `
    $(bookTable).prepend(row);
}

function fillBookTable(bookTable, bookList) {
    bookList.forEach(book => {
        let row = document.createElement("tr");
        row.innerHTML = `
            <td>${book.name}</td>
            <td>${book.author}</td>
            <td>${book.description}</td>
        `
        bookTable.appendChild(row);
    })
}

function notify(message) {
    if (!window.Notification) {
        console.log('Browser does not support notifications.');
    } else {
        // check if permission is already granted
        if (Notification.permission === 'granted') {
            // show notification here
            var notify = getNotification(message)
        } else {
            // request permission from user
            Notification.requestPermission().then(function (p) {
                if (p === 'granted') {
                    // show notification here
                    var notify = getNotification(message)
                } else {
                    console.log('User blocked notifications.');
                }
            }).catch(function (err) {
                console.error(err);
            });
        }
    }
}

function getNotification(message) {
    return new Notification(message, {
                   body: message,
               });
}
