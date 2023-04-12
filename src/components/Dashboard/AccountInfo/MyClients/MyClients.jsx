import * as React from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import { Stack } from '@mui/system';
// import { Button } from 'reactstrap';
import { Box, Typography } from '@mui/material';
import { Button, Col, FormGroup, InputGroup, Row } from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal';
// import Modal from '@mui/material/Modal'
import './MyClients.css';
import { TextField } from '@mui/material';
// import { Form, Label, FormGroup, Input, CloseButton } from 'reactstrap'
import { Helmet } from 'react-helmet';
import { BsCalendar2CheckFill, BsCalendarDate, BsCash, BsCashCoin, BsCheck2Square, BsEnvelopeFill, BsHouseFill, BsInfoCircle, BsInfoLg, BsKey, BsKeyFill, BsMailbox, BsPeopleFill, BsPerson, BsPersonBadge, BsPersonCheck, BsPersonCheckFill, BsPersonFill, BsPersonX, BsPhoneFill, BsPlusCircleFill, BsTelephoneFill } from 'react-icons/bs';
import { useState } from 'react';
import { Form, Input } from 'reactstrap';
import { API_URL } from '../../../../Constants/Index';



const columns = [

    {
        id: 'agentNo',
        label: 'Agent No',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'username',
        label: 'User Name',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'agentNames',
        label: 'Agent Names',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'phone',
        label: 'Mobile Phone',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'accountBal',
        label: 'Account Bal.(Rwf)',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'active',
        label: 'Active',
        minWidth: 50,
        align: 'left',
        // format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'lastLogin',
        label: 'Last Login',
        minWidth: 150,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'dateJoined',
        label: 'Date Joined',
        minWidth: 150,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
];

function createData(agentNo, username, agentNames, phone, accountBal, active, lastLogin, dateJoined) {
    return { agentNo, username, agentNames, phone, accountBal, active, lastLogin, dateJoined };
}

const rows = [
    createData('0000006758', 'Rosine', 'Rosine', '0785467789', 5000, '1', 34567890, 34485990),
    createData('0000006758', 'Rosine', 'Rosine', '0785467789', 5000, '1', 34567890, 34485990),
    createData('0000006758', 'Rosine', 'Rosine', '0785467789', 5000, '1', 34567890, 34485990),

];

export default function MyClients({ token }) {
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    // const [open, setOpen] = React.useState(false)
    // const handleOpen = () => setOpen(true)
    // const handleClose = () => setOpen(false)


    const [show, setShow] = useState(false)

    const handleClose = () => setShow(false)
    const handleShow = () => setShow(true)

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    // refresh

    const refresh = () => window.location.reload(true)

    // clients list

    const [myclients, setMyclients] = useState()

    // form data

    const [responsemessage, setResponsemessage] = useState()

    const [username, setUsername] = useState()
    const [firstname, setFirstname] = useState()
    const [email, setEmail] = useState()
    const [password, setPassword] = useState()
    const [phonenumber, setPhonenumber] = useState()
    const [telephone, setTelephone] = useState()
    const [address, setAddress] = useState()
    const [description, setDescription] = useState()

    // function setToken(token)


    function add_client(e) {

        e.preventDefault();

        // var myHeaders = new Headers();
        // myHeaders.append("Authorization", "Token 1a72643e7023c8dac2e9a3eb9245e31a763e279b");

        var formdata = new FormData();
        formdata.append("user_name", username);
        formdata.append("first_name", firstname);
        formdata.append("email", email);
        formdata.append("password", password);
        formdata.append("phone_number", phonenumber);
        formdata.append("telephone", phonenumber);
        formdata.append("address", address);
        formdata.append("description", description);


        var requestOptions = {
            method: 'POST',
            mode: 'cors',
            headers: {
                Authorization: `Token ${token}`,
            },
            body: formdata,
            redirect: 'follow'
        };

        fetch(API_URL + "appaddmyclient", requestOptions)
            .then(response => response.json())
            .then(result => setResponsemessage(convertToJson(result)))
            .catch(error => console.log('error', error));
    }



    function convertToJson(response) {
        let json_string = JSON.stringify(response[0]).replace("success", "'success'");
        json_string = json_string.replace("msg", "'msg'");
        const data = json_string.replace(/'/g, '"');
        const newstr = data.substring(1, data.length - 1)
        console.log(newstr)
        return JSON.parse(newstr);

    }


    var formdata = new FormData();
    formdata.append("start", "1332407871");
    formdata.append("limit", "1679476671");
    formdata.append("pattern", "");

    var requestOptions = {
        method: 'POST',
        headers: {
            Authorization: `Token ${token}`
        },
        body: formdata,
        redirect: 'follow'
    };

    fetch(API_URL +"appgetmyclients", requestOptions)
        .then(response => response.json())
        .then(result => setMyclients(JSON.parse(result)))
        // .then(result=>console.log(JSON.parse(result).response))
        .catch(error => console.log('error', error));



    return (
        <Paper sx={{ width: '95%', margin: '1rem', overflowX: 'auto',overflowY:'auto' }}>
            <div className='table-headers'>
                <div className='table-title'><BsPeopleFill/> My Clients</div>
            <Stack spacing={2} direction="row" sx={{ margin: "1rem", paddingTop: "1rem" }}>
                <Button onClick={handleShow} className="app-buttons mt-0 text-dark ">ADD</Button>

                {/* Add Modal */}

                <Modal show={show} onHide={handleClose} className="modal">
                    <Modal.Header className='modal-header' closeButton>
                        <span className='contact-modal-title'><BsPlusCircleFill /> ADD CLIENT</span>
                    </Modal.Header>

                    <form encType='multipart/form-data' onSubmit={add_client}>

                        <Modal.Body>


                            <div className='d-flex flex-row mb-3 gap-2'>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsPersonFill />
                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder=''
                                        name=''
                                        // onChange={(e)=>handle(e)}
                                        // onChange={(e) => setUsername(e.target.value)}
                                        value="(New..)"
                                        readOnly

                                    />
                                </InputGroup>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsPersonFill />
                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Names'
                                        name='firstname'
                                        // onChange={(e)=>handle(e)}
                                        onChange={(e) => setFirstname(e.target.value)}
                                        value={firstname}
                                        required
                                    />
                                </InputGroup>

                            </div>
                            <div className='d-flex flex-row mb-3 gap-2'>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsPersonBadge />

                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Username'
                                        name='username'
                                        // onChange={(e)=>handle(e)}
                                        onChange={(e) => setUsername(e.target.value)}
                                        value={username}
                                        required
                                    />
                                </InputGroup>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsPersonCheckFill />
                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Active'
                                        name='active'
                                        // onChange={(e)=>handle(e)}
                                        // onChange={(e) => setFirstname(e.target.value)}
                                        // value={firstname}
                                        readOnly
                                    />
                                </InputGroup>


                            </div>

                            <div className='d-flex flex-row mb-3 gap-2'>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsTelephoneFill />
                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Mobile Phone'
                                        name='phonenumber'
                                        // onChange={(e)=>handle(e)}
                                        onChange={(e) => setPhonenumber(e.target.value)}
                                        value={phonenumber}
                                        required
                                    />
                                </InputGroup>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsEnvelopeFill />
                                    </InputGroup.Text>

                                    <Input
                                        type='email'
                                        className='form-control'
                                        placeholder='E-mail'
                                        name='email'
                                        // onChange={(e)=>handle(e)}
                                        onChange={(e) => setEmail(e.target.value)}
                                        value={email}
                                        required
                                    />
                                </InputGroup>


                            </div>

                            <div className='d-flex flex-row mb-3 gap-2'>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsHouseFill />
                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Address'
                                        name='address'
                                        // onChange={(e)=>handle(e)}
                                        onChange={(e) => setAddress(e.target.value)}
                                        value={address}
                                    />
                                </InputGroup>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsCash />
                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Account Bal'
                                        name='accountbal'
                                        // onChange={(e)=>handle(e)}
                                        // onChange={(e) => setPhonenumber(e.target.value)}
                                        // value={phonenumber}
                                        readOnly
                                    />
                                </InputGroup>


                            </div>

                            <div className='d-flex flex-row mb-3 gap-2'>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsCashCoin />
                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Commission'
                                        name='commision'
                                        // onChange={(e)=>handle(e)}
                                        // onChange={(e) => setPhonenumber(e.target.value)}
                                        // value={phonenumber}
                                        readOnly
                                    />
                                </InputGroup>


                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsInfoCircle />
                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Desctiption'
                                        name='description'
                                        // onChange={(e)=>handle(e)}
                                        onChange={(e) => setDescription(e.target.value)}
                                        value={description}
                                    />
                                </InputGroup>

                            </div>

                            <div className='d-flex flex-row mb-3 gap-2'>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsKeyFill />
                                    </InputGroup.Text>

                                    <Input
                                        type='password'
                                        className='form-control'
                                        placeholder='Password'
                                        name='password'
                                        // onChange={(e)=>handle(e)}
                                        onChange={(e) => setPassword(e.target.value)}
                                        value={password}
                                    />
                                </InputGroup>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsCalendarDate />
                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Created'
                                        name='created'
                                        // onChange={(e)=>handle(e)}
                                        // onChange={(e) => setPhonenumber(e.target.value)}
                                        // value={phonenumber}
                                        readOnly
                                    />
                                </InputGroup>


                            </div>

                            <div className='d-flex flex-row mb-3 gap-2'>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsKey />
                                    </InputGroup.Text>

                                    <Input
                                        type='password'
                                        className='form-control'
                                        placeholder='Confirm Password'
                                        name='confirmpassword'
                                        // onChange={(e)=>handle(e)}
                                        // onChange={(e) => setPhonenumber(e.target.value)}
                                        // value={phonenumber}
                                        required
                                    />
                                </InputGroup>

                                <InputGroup>
                                    <InputGroup.Text>
                                        <BsCalendar2CheckFill />
                                    </InputGroup.Text>

                                    <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Last Login'
                                        name='lastlogin'
                                    // onChange={(e)=>handle(e)}
                                    // onChange={(e) => setPhonenumber(e.target.value)}
                                    // value={phonenumber}
                                    />
                                </InputGroup>


                            </div>


                            <div>
                                {responsemessage && (
                                    <span><i><b>* {responsemessage.msg}</b></i></span>
                                )}
                            </div>



                        </Modal.Body>
                        <Modal.Footer>

                            <button className='btn btn-dark opacity-60' type='submit'>Save Changes</button>

                            <Button className='btn btn-dark opacity-60' onClick={handleClose}> Cancel</Button>

                        </Modal.Footer>
                    </form>
                </Modal>


                {/* end modal */}


                <button className='app-buttons' variant="contained" color='dark'>View & Modify</button>
                <button className='app-buttons' onClick={refresh} variant="contained" color='dark'>Refresh</button>
            </Stack >
            </div>

            {/* <table>
                {myclients && myclients.response.map((clients) => (
                  <tr key={clients.pk}>
                    <td className="table-data">{clients.pk}</td>
                    <td>{clients.pk}</td>

                  </tr>
                ))}
            </table> */}
            <TableContainer sx={{ maxHeight: 440 }}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead className='my-clients-table'>

                        <TableRow>
                            {columns.map((column) => (
                                <TableCell
                                    key={column.id}
                                    align={column.align}
                                    style={{ top: 5, minWidth: column.minWidth }}
                                >
                                    {column.label}
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {/* {rows
                            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                            .map((row) => {
                                return (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                                        {columns.map((column) => {
                                            const value = row[column.id];
                                            return (
                                                <TableCell key={column.id} align={column.align}>
                                                    {column.format && typeof value === 'text'
                                                        ? column.format(value)
                                                        : value}
                                                </TableCell>
                                            );
                                        })}
                                    </TableRow>
                                );
                            })} */}

                        {myclients && 
                            myclients.response
                            // .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                              .map((clients)=>{
                                        return (
                                            <TableRow hover role="checkbox" tabIndex={-1} key={clients.pk}>
                                                <TableCell>{clients.fields.customerno}</TableCell>
                                                <TableCell>{clients.fields.username}</TableCell>
                                                <TableCell>{clients.fields.first_name}  {clients.fields.last_name}</TableCell>
                                                <TableCell>{clients.fields.phone}</TableCell>
                                                <TableCell>{clients.fields.smsbalance}</TableCell>
                                                <TableCell>{clients.fields.is_active}</TableCell>
                                                <TableCell>{clients.fields.date_joined}</TableCell>
                                                <TableCell>{clients.fields.last_login}</TableCell>

                                 
                                            </TableRow>
                                        )
                                     })}
                            
{/*                             
                        {myclients && myclients.response.map((clients) => (
                            <tr key={clients.pk}>
                                <td className="table-data">{clients.pk}</td>
                                <td>{clients.customerno}</td>

                            </tr>
                        ))} */}

                        
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={myclients && myclients.total}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />

             {/* {myclients && (<span>{myclients.total}</span>)} */}
        </Paper >
    );
}