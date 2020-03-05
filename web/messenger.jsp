
<%@page import="dbmanager.AdminDbManager"%>
<%@page import="dbmanager.UserDbManager"%>
<%@page import="model.Admin"%>
<%@page import="model.User"%>
<%@page import="model.Messages"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dbmanager.MessageDbManager"%>
<!DOCTYPE html>
<%
    
    HttpSession ses = request.getSession();
    if(ses.getAttribute("USER_TYPE")==null){
        response.sendRedirect("login.jsp");
        return;
    }
    
    
    
    
    int receiver_id = -1;
    if(request.getParameter("id")!=null)
        receiver_id = Integer.parseInt(request.getParameter("id"));
    
    int sender_id;
    
   
    
    int sender ;//0 for admin 1 for user
    String usertype = (String)ses.getAttribute("USER_TYPE");
    if(usertype.compareToIgnoreCase("USER")==0){
        sender = 1;
        User user = (User)ses.getAttribute("USER");
        sender_id = user.getUserId();
    }else{
        sender = 0;
        Admin admin = (Admin)ses.getAttribute("USER");
        sender_id = admin.getAdminId();
    }
    int admin_id;
    int user_id;
    if (sender == 0) {
        admin_id = sender_id;
        user_id = receiver_id;
    } else {
        user_id = sender_id;
        admin_id = receiver_id;
    }

    MessageDbManager mdb = new MessageDbManager();

    ArrayList<Messages> arr = mdb.getAllMessages(admin_id, user_id);
    int latest_id;
    if(arr.size()==0){
        latest_id = -1;
    }else
        latest_id = arr.get((arr.size() - 1)).getId();


%>


<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>RTS</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">





        <!-- summernote -->
        <link rel="stylesheet" href="plugins/summernote/summernote-bs4.css">


        <!-- Font Awesome -->
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/adminlte.min.css">


        <!-- Tempusdominus Bbootstrap 4 -->
        <link rel="stylesheet" href="plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
        <!-- SweetAlert2 -->
        <link rel="stylesheet" href="plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
    </head>
    <body class="hold-transition sidebar-mini layout-fixed">
        <div class="wrapper">

            <!-- /.navbar -->


            <div class="">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1>Conversations</h1>
                            </div>
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                                    <li class="breadcrumb-item active">Conversations</li>
                                </ol>
                            </div>
                        </div>
                    </div><!-- /.container-fluid -->
                </section>

                <!-- /.content-header -->

                <!-- Main content -->
                <section class="content">
                    <!-- DIRECT CHAT -->

                    <input type="hidden" id ="latest_id" value="<%=latest_id%>" />

                    <!--/.direct-chat -->

                    <div class="row">

                        <div class="col-md-4">
                            <div class="card direct-chat direct-chat-primary">
                                <div class="card-header">
                                    <h3 class="card-title">Contacts</h3>


                                </div>
                                <div class="content overflow-auto" id="contactlist" style="height:454px">
                                    <ul class="contacts-list" style="background-color:#1f2d3dd1 ">
                                <%
                                    if(usertype.compareToIgnoreCase("ADMIN")==0){
                                        ArrayList<User> list = UserDbManager.getAllUserInformation();
                                        for(User u : list){
                                            
                                        
                                        %>
                                        <!-- Contacts are loaded here -->
                                                    <li>
                                                        <a href="messanger.jsp?id=<%=u.getUserId() %>">
                                                            <img class="contacts-list-img" src="dist/img/danish.jpg">

                                                            <div class="contacts-list-info">
                                                                <span class="contacts-list-name">
                                                                    <%=u.getName() %>
                                                                    
                                                                </span>
                                                                
                                                            </div>
                                                            <!-- /.contacts-list-info -->
                                                        </a>
                                                    </li>
                                        <!-- End Contact Item -->
                                <%      }
                                    }else{
                                        ArrayList<Admin> list = AdminDbManager.getAllAdminInformation();
                                        for(Admin a: list){
                                        %>
                                        <!-- Contacts are loaded here -->
                                        
                                                <li>
                                                    <a  href="messanger.jsp?id=<%=a.getAdminId() %>">
                                                        <img class="contacts-list-img" src="dist/img/danish.jpg">

                                                        <div class="contacts-list-info">
                                                            <span class="contacts-list-name">
                                                                <%=a.getName() %>
                                                                
                                                            </span>
                                                           
                                                        </div>
                                                        <!-- /.contacts-list-info -->
                                                    </a>
                                                </li>
                                                
                                        <!-- End Contact Item -->
                                <%
                                        }
                                    }
                                %>
                                    </ul>
                                </div>
                                

                            </div>
                        </div>
                        <!-- /.col -->
                        <div class="col-md-8">
                            <div class="card direct-chat direct-chat-primary" id="" style="height:500px!important">
                                <div class="card-header">
                                    <h3 class="card-title">Direct Chat</h3>


                                </div>
                                <!-- /.card-header -->
                                <% 
                                    if(receiver_id!=-1)
                                    {
                                       %>
                                        <div class="card-body" id="">
                                    <!-- Conversations are loaded here -->
                                    <div class="direct-chat-messages" id="chatdiv" style="height:392px!important">
                                        <!-- Message. Default to the left -->
                                        <%for (Messages x : arr) {%>
                                        <%
                                            if (x.getSender() != sender) {
                                        %>
                                        <div class="direct-chat-msg">
                                            <div class="direct-chat-infos clearfix">
                                                <span class="direct-chat-name float-left">Receiver id =<%
                                                    if (x.getSender() == 0) {
                                                        out.println(x.getUser_id());
                                                    } else {
                                                        out.println(x.getAdmin_id());
                                                    }
                                                    %></span>
                                                <span class="direct-chat-timestamp float-right"><%= x.getDateTime()%></span>
                                            </div>
                                            <!-- /.direct-chat-infos -->
                                            <img class="direct-chat-img" src="dist/img/user1-128x128.jpg" alt="message user image">
                                            <!-- /.direct-chat-img -->
                                            <div class="direct-chat-text">
                                                <%= x.getMessage()%>
                                            </div>
                                            <!-- /.direct-chat-text -->
                                        </div>
                                        <!-- /.direct-chat-msg -->
                                        <%} else {%>
                                        <!-- Message to the right -->
                                        <div class="direct-chat-msg right">
                                            <div class="direct-chat-infos clearfix">
                                                <span class="direct-chat-name float-right">Sender: <%
                                                    if (x.getSender() == 1) {
                                                        out.println(x.getUser_id());
                                                    } else {
                                                        out.println(x.getAdmin_id());
                                                    }
                                                    %></span>
                                                <span class="direct-chat-timestamp float-left"><%=x.getDateTime()%></span>
                                            </div>
                                            <!-- /.direct-chat-infos -->
                                            <img class="direct-chat-img" src="dist/img/user8-128x128.jpg" alt="message user image">
                                            <!-- /.direct-chat-img -->
                                            <div class="direct-chat-text">
                                                <%=x.getMessage()%>
                                            </div>
                                            <!-- /.direct-chat-text -->
                                        </div>
                                        <!-- /.direct-chat-msg -->
                                        <%}
                                            }
                                        %>

                                    </div>
                                    <!--/.direct-chat-messages-->


                                </div>
                                         <!-- /.card-body -->
                                <div class="card-footer">

                                    <div class="input-group">
                                        <input type="text" id ="message" name="message" placeholder="Type Message ..." class="form-control">
                                        <span class="input-group-append">
                                            <button type="button" onclick="messageSend()" class="btn btn-primary">Send</button>
                                        </span>
                                    </div>
                                   

                                </div>
                                        <%   
                                    }
                                %>
                                
                               
                                <!-- /.card-footer-->
                            </div>


                        </div>
                        <!-- /.col -->

                    </div>
                    <!-- /.row -->
                </section>
                <!-- /.content -->
                <!-- /.content-wrapper -->


                <!-- Control Sidebar -->
                <aside class="control-sidebar control-sidebar-dark">
                    <!-- Control sidebar content goes here -->
                </aside>
                <!-- /.control-sidebar -->
            </div>
            <!-- ./wrapper -->

            <!-- jQuery -->
            <script src="plugins/jquery/jquery.min.js"></script>
            <!-- jQuery UI 1.11.4 -->
            <script src="plugins/jquery-ui/jquery-ui.min.js"></script>
            <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
            <script>
                                                $.widget.bridge('uibutton', $.ui.button)
            </script>
            <!-- Bootstrap 4 -->
            <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
            <!-- Select2 -->
            <script src="plugins/select2/js/select2.full.min.js"></script>
            <!-- Bootstrap4 Duallistbox -->
            <script src="plugins/bootstrap4-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>
            
            <!-- InputMask -->
            <script src="plugins/moment/moment.min.js"></script>
            <script src="plugins/inputmask/min/jquery.inputmask.bundle.min.js"></script>
            <!-- date-range-picker -->
            <script src="plugins/daterangepicker/daterangepicker.js"></script>
            <!-- Sweet-alert -->
            <script src="plugins/sweetalert2/sweetalert2.min.js"></script>
            <!-- bootstrap color picker -->
            <script src="plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.min.js"></script>
            <!-- Tempusdominus Bootstrap 4 -->
            <script src="plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
            <!-- Bootstrap Switch -->
            <script src="plugins/bootstrap-switch/js/bootstrap-switch.min.js"></script>
            <!-- AdminLTE App -->

            <!-- Page script -->
            <script type="text/javascript">
                                                $('#chatdiv').scrollTop($('#chatdiv')[0].scrollHeight);
            </script>

            <script type="text/javascript">
                function messageSend()
                {
                    var user_id = <%=user_id%>;
                    var admin_id = <%=admin_id%>;
                    var sender = <%=sender%>;
                    var $message = $("#message").val();
                    var $type = "success";
                    var $alertmessage = "Successfully sent";
                    //alert($message);
                    if ($message == "" || $message == null)
                    {
                        $type = "error";
                        $alertmessage = "Message Is Empty";
                    }
                    var obj = {
                        "user_id": user_id,
                        "admin_id": admin_id,
                        "message": $message
                    }
                    $.ajax({
                        type: "post",
                        url: "SendMessage",
                        //cache: false,
                        data: JSON.stringify(obj),
                        success: function (result) {
                            if (result != null)
                            {
                                result = JSON.parse(result);
                                var $chat = "<div class='direct-chat-msg right'><div class='direct-chat-infos clearfix'><span class='direct-chat-name float-right'>Sender Name</span><span class='direct-chat-timestamp float-left'>" + result.dateTime + "</span></div><img class='direct-chat-img' src='dist/img/user8-128x128.jpg' alt='message user image'><div class='direct-chat-text'>" + result.message + "</div></div>";
                                $("#message").val("");
                                $("#chatdiv").append($chat);
                                $('#chatdiv').scrollTop($('#chatdiv')[0].scrollHeight);
                                $alertmessage = "Message Sent";
                                $("#latest_id").val(result.id);
                                //alert($("#latest_id").val());
                                console.log(result);
                            } else
                            {
                                alert("cannot proceed request server down or session expire");
                            }

                        },
                        error: function () {
                            $type = "error";
                            $alertmessage = "something went wrong";
                        }
                    });



                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 3000
                    });
                    Toast.fire({
                        type: $type,
                        title: $alertmessage
                    })

                }
            </script>
            <script type="text/javascript">
                function loadChat(n, i)
                {
                    var $rname = n;
                    var $image = i;

                }
                function fun()
                {
                    var char = "ab";
                }
            </script>
            <script type="text/javascript">
                function messageRecive()
                {
                    var user_id = <%=user_id%>;
                    var admin_id = <%=admin_id%>;
                    var id = $("#latest_id").val();
                    var obj = {
                        "admin_id": user_id,
                        "user_id": admin_id,
                        "message_id": id
                    }

                    $.ajax({
                        type: "post",
                        url: "RecieveMessageServlet",
                        //cache: false,
                        data: JSON.stringify(obj),
                        success: function ($res) {
                            if ($res != null)
                            {
                               
                               
                                var $result = JSON.parse($res);
                                
                                if($result.length>0){
                                    const Toast = Swal.mixin({
                                        toast: true,
                                        position: 'top-end',
                                        showConfirmButton: false,
                                        timer: 3000
                                    });
                                    Toast.fire({
                                        type: "Notification",
                                        title: "new Message Recieved"
                                    })
                                }
                                
                                $latestId = $result[$result.length-1].id;
                                $("#latest_id").val($latestId);
                               
                                 for (var i = 0; i < $result.length; i++)
                                {
                                    var $chat = "<div class='direct-chat-msg left'><div class='direct-chat-infos clearfix'><span class='direct-chat-name float-left'>Sender Name</span><span class='direct-chat-timestamp float-right'>" + $result[i].dateTime + "</span></div><img class='direct-chat-img' src='dist/img/user8-128x128.jpg' alt='message user image'><div class='direct-chat-text'>" + $result[i].message + "</div></div>";
                                    $("#chatdiv").append($chat);
                                    id = $result[i].id;
                                }
                            }


                        },
                        error: function () {
                        }
                    }
                    );
                }




            </script>
            <script>
                $(function() {
                    
                     setInterval(messageRecive, 2000);
                     messageRecive();
                });
            </script>



    </body>
</html>
