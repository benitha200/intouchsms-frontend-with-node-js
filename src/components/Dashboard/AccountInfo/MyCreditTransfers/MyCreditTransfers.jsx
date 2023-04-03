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
// import { Box, FormGroup, TextField, Typography } from '@mui/material';
// import Modal from '@mui/material/Modal'
import './MyCreditTransfers.css'
import { DataGrid } from '@mui/x-data-grid'
import { Button, Col, Form, FormGroup, InputGroup, Row } from 'react-bootstrap';
import { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import { BsArrowLeftRight, BsCash, BsCashCoin, BsEnvelopeFill, BsHouseDoorFill, BsInfo, BsInfoCircle, BsPersonCircle, BsPersonFill, BsSearch, BsTelephone } from 'react-icons/bs';


const columns = [

    {
        id: 'transferNo',
        label: 'Transfer No',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'customerNumber',
        label: 'Customer No',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'customerName',
        label: 'Customer Name',
        minWidth: 150,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'phone',
        label: 'Phone',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'email',
        label: 'Email',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'amountTransfered',
        label: 'Amount Transfered',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'created',
        label: 'Created',
        minWidth: 150,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
];

function createData(id, transferNo, customerNumber, customerName, phone, email, amountTransfered, created) {
    return { id, transferNo, customerNumber, customerName, phone, email, amountTransfered, created };
}

const rows = [
    createData(1, '0000006758', '235679875', 'ISCO', '0785467789', 'isco@isco.co.rw', 5000, 34567890),
    createData(2, '0000006758', '235679875', 'ISCO', '0785467789', 'isco@isco.co.rw', 5000, 34567890),
    createData(3, '0998435934', '894348759', 'DMC Hospital', '0786743783', 'dmc@dmc.co.rw', 5600, 89743403),


];

const styles = {
    root: {
        fontFamily: '"Helvetica Neue", Helvetica, Roboto, Arial, sans-serif',
        fontSize: '10px',
        fontWeight: 'normal',
    },
};
export default function MyCreditTransfers({ token }) {
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    // const [open, setOpen] = React.useState(false)
    // const handleOpen = () => setOpen(true)
    // const handleClose = () => setOpen(false)


    //modal
    const [show, setShow] = useState(false)
    const handleClose = () => setShow(false)
    const handleShow = () => setShow(true)

    // credit transfers

    const [creditTransfers, setCreditTransfers] = useState()

    //form

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    // table

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    var formdata = new FormData();
    formdata.append("start", "1332407871");
    formdata.append("limit", "1679476671");

    var requestOptions = {
        method: 'POST',
        headers: {
            'Authorization': `Token ${token}`,
        },
        body: formdata,
        redirect: 'follow'
    };

    fetch("http://127.0.0.1:8000/api/appgetcredittransfers", requestOptions)
        .then(response => response.json())
        .then(result => setCreditTransfers(JSON.parse(result)))
        .catch(error => console.log('error', error));



    return (
        <Paper sx={{ width: '98%', margin: '0.5rem', overflowX: 'auto' }}>

            <div className="table-headers">
                <span className='table-title'>My Credit Transfers</span>

                <Stack spacing={2} direction="row" justifyContent="space-around" sx={{ height: "4rem", padding: "1rem" }}>

                    {/* <Button variant="outlined" onClick={setOpen} className='app-buttons text-dark m-0'>Add My Credit Transfer</Button> */}
                    <Button onClick={setShow} className='app-buttons text-dark m-0 w-50'>Add My Credit Transfer</Button>
                    <button className='app-buttons w-25' variant="contained" color='dark'>View & Modify</button>
                    <button className='app-buttons w-25' variant="contained" color='dark'>Refresh</button>

                    <div className="input-group">
                        <div className="form-outline">
                            <input id="search-input" type="search" placeholder='Search' className="form-control" />
                            {/* <label className="form-label" for="form1">Search</label> */}
                        </div>
                        <button id="search-button" type="button">
                            <BsSearch variant="inherit"/>
                        </button>
                    </div>
                    {/* Add Modal */}

                    <Modal show={show} onHide={handleClose} className="modal">
                        <Modal.Header className='modal-header' closeButton>
                            <span className='contact-modal-title'><i className='fa fa-key'></i> ADD CREDIT TRANSFER</span>
                        </Modal.Header>
                        <Form >
                            <Modal.Body>


                                {/* <InputGroup className="mb-3">
                        <InputGroup.Text>
                          <BsSearch />
                        </InputGroup.Text>
                        <Form.Control placeholder="Search" aria-label="Search" />
                      </InputGroup> */}
                                <span>Credit Transfer Info</span>
                                <hr />
                                <div className='d-flex flex-row gap-2 mb-3'>
                                    <InputGroup>
                                        {/* <Form.Label>New Password</Form.Label> */}
                                        <InputGroup.Text>
                                            <BsArrowLeftRight />
                                        </InputGroup.Text>
                                        <Form.Control type='text' placeholder='Transfer No' required></Form.Control>
                                    </InputGroup>

                                    <InputGroup>
                                        <InputGroup.Text><BsInfo /></InputGroup.Text>
                                        {/* <Form.Label>Confirm New password</Form.Label> */}
                                        <Form.Control type='textarea' placeholder='Description' required></Form.Control>
                                    </InputGroup>
                                </div>
                                <div className='d-flex flex-row gap-2 mb-3'>
                                    <InputGroup>
                                        {/* <Form.Label>New Password</Form.Label> */}
                                        <InputGroup.Text>
                                            <BsCash />
                                        </InputGroup.Text>
                                        <Form.Control type='text' placeholder='Account Balance' required></Form.Control>
                                    </InputGroup>

                                    <InputGroup>
                                        <InputGroup.Text><BsCashCoin /></InputGroup.Text>
                                        {/* <Form.Label>Confirm New password</Form.Label> */}
                                        <Form.Control type='textarea' placeholder='Transfer Amount' required></Form.Control>
                                    </InputGroup>
                                </div>

                                <span className="">Credit Transfer Info</span>
                                <hr />

                                <div className='d-flex flex-row gap-2 mb-3'>
                                    <InputGroup>
                                        {/* <Form.Label>New Password</Form.Label> */}
                                        <InputGroup.Text>
                                            <BsPersonCircle />
                                        </InputGroup.Text>
                                        <Form.Control className='m-0 h-100' type='text' placeholder='Customer No' required></Form.Control>
                                    </InputGroup>

                                    <InputGroup>
                                        <Button className='btn btn-dark opacity-50 m-0 h-100 w-100'>Select</Button>
                                    </InputGroup>
                                </div>
                                <div className='d-flex flex-row gap-2 mb-3'>
                                    <InputGroup>
                                        {/* <Form.Label>New Password</Form.Label> */}
                                        <InputGroup.Text>
                                            <BsPersonFill />
                                        </InputGroup.Text>
                                        <Form.Control type='text' placeholder='Customer name' required></Form.Control>
                                    </InputGroup>

                                    <InputGroup>
                                        <InputGroup.Text><BsTelephone /></InputGroup.Text>
                                        {/* <Form.Label>Confirm New password</Form.Label> */}
                                        <Form.Control type='text' placeholder='Phone' required></Form.Control>
                                    </InputGroup>
                                </div>
                                <div className='d-flex flex-row gap-2 mb-3'>
                                    <InputGroup>
                                        {/* <Form.Label>New Password</Form.Label> */}
                                        <InputGroup.Text>
                                            <BsEnvelopeFill />
                                        </InputGroup.Text>
                                        <Form.Control type='text' placeholder='Email' required></Form.Control>
                                    </InputGroup>

                                    <InputGroup>
                                        <InputGroup.Text><BsHouseDoorFill /> </InputGroup.Text>
                                        {/* <Form.Label>Confirm New password</Form.Label> */}
                                        <Form.Control type='text' placeholder='Address' required></Form.Control>
                                    </InputGroup>
                                </div>







                            </Modal.Body>
                            <Modal.Footer>

                                <Button className='btn btn-dark opacity-60' type='submit'>Save Changes</Button>

                                <Button className='btn btn-dark opacity-60' onClick={handleClose}> Cancel</Button>

                            </Modal.Footer>
                        </Form>

                    </Modal>


                </Stack>
            </div>


            <TableContainer sx={{ maxHeight: 440 }}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead className='my-clients-table'>

                        <TableRow>
                            {columns.map((column) => (
                                <TableCell
                                    key={column.id}
                                    align={column.align}
                                    style={{ top: 0, minWidth: column.minWidth }}
                                >
                                    {column.label}
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {creditTransfers &&
                            creditTransfers.response
                                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                .map((creditTransfer) => {
                                    return (
                                        <TableRow hover role="checkbox" tabIndex={-1} key={creditTransfer.transferno}>
                                            <TableCell>{creditTransfer.transferno}</TableCell>
                                            <TableCell>{creditTransfer.receipientcustomerno}</TableCell>
                                            <TableCell>{creditTransfer.receipientcustomer}</TableCell>
                                            <TableCell>{creditTransfer.receipientmobilephone}</TableCell>
                                            <TableCell>{creditTransfer.receipientemail}</TableCell>
                                            <TableCell>{creditTransfer.amount} {creditTransfer.symbol}</TableCell>
                                            <TableCell>{creditTransfer.created}</TableCell>


                                        </TableRow>
                                    )
                                })}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={creditTransfers && creditTransfers.total}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Paper >
    );
}