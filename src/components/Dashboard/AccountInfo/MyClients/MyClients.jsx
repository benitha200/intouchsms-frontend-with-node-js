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
import { BsCalendar2CheckFill, BsCalendarDate, BsCash, BsCashCoin, BsCheck2Square, BsEnvelopeFill, BsHouseFill, BsInfoCircle, BsInfoLg, BsKey, BsKeyFill, BsMailbox, BsPerson, BsPersonBadge, BsPersonCheck, BsPersonCheckFill, BsPersonFill, BsPersonX, BsPhoneFill, BsPlusCircleFill, BsTelephoneFill } from 'react-icons/bs';
import { useState } from 'react';
import { Form, Input } from 'reactstrap'

const columns = [

    {
        id: 'agentNo',
        label: 'Agent No',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'username',
        label: 'User Name',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'agentNames',
        label: 'Agent Names',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'phone',
        label: 'Mobile Phone',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'accountBal',
        label: 'Account Bal.(Rwf)',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'active',
        label: 'Active',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'lastLogin',
        label: 'Last Login',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'dateJoined',
        label: 'Date Joined',
        minWidth: 170,
        align: 'right',
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

        fetch("http://127.0.0.1:8000/api/appaddmyclient", requestOptions)
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


    return (
        <Paper sx={{ width: '80%', margin: '1rem', overflowX: 'auto' }}>


            <Stack spacing={2} direction="row" sx={{ margin: "1rem", paddingTop: "1rem" }}>
                <Button onClick={handleShow} className="app-buttons mt-0 text-dark ">ADD</Button>
                {/* <Button variant="outlined" className='app-buttons text-dark mt-0' color='secondary' onClick={handleOpen}>Add</Button> */}
                {/* <Modal
                    keepMounted
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="keep-mounted-modal-title"
                    aria-describedby="keep-mounted-modal-body"

                >
                    <Box className='my-clients-modal' sx>
                        <div className="modal-title">
                            <Typography>Add Client</Typography>
                        </div>

                        <div className='modal-form'>
                            <Stack>
                                <Form>
                                    <Stack direction="row" gap={1} className='form-row'>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Customer No."
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Names"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="User Name"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Active"
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Mobile Phone"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="E-mail"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Address"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Account Bal"
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                    </Stack>

                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Comission"
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Description"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Password"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Created"
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Confirm Password"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Last Login"
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                    </Stack>



                                    <Stack direction="row" gap={1}>
                                        <button className='app-modal-buttons'>Save and Close</button>
                                        <button className='app-modal-buttons'>Save and New</button>
                                        <button className='app-modal-buttons'>Cancel</button>
                                    </Stack>

                                </Form>
                            </Stack>
                        </div>

                    </Box>


                </Modal > */}


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
            <TableContainer sx={{ maxHeight: 440 }}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead className='my-clients-table'>

                        <TableRow>
                            {columns.map((column) => (
                                <TableCell
                                    key={column.id}
                                    align={column.align}
                                    style={{ top: 57, minWidth: column.minWidth }}
                                >
                                    {column.label}
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows
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
                            })}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={rows.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Paper >
    );
}