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
import { Button } from 'reactstrap';
import { Box, FormGroup, TextField, Typography } from '@mui/material';
import Modal from '@mui/material/Modal'
import './MyTransactions.css'
import { Form } from 'react-bootstrap';
import { DataGrid } from '@mui/x-data-grid'
import { useState } from 'react';
import axios from 'axios';
import { CircularProgress } from '@mui/material';

const columns = [

    {
        id: 'transaction',
        label: 'Transaction',
        minWidth: 50,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'customerNo',
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
        id: 'smsQuantity',
        label: 'SMS Quantity',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'openingBalance',
        label: 'Email',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'amount',
        label: 'Amount ',
        minWidth: 50,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'closingBalance',
        label: 'ClosingBalance',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'created',
        label: 'Created',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'beneficiary',
        label: 'Beneficiary',
        minWidth: 100,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
];
// Dummy data

// function createData(id, transaction, customerNo, customerName, smsQuantity, openingBalance, amount, closingBalance, created) {
//     return { id, transaction, customerNo, customerName, smsQuantity, openingBalance, amount, closingBalance, created };
// }

// const rows = [
//     createData(1, '0000006758', '3489540', 'Rosine', '12000', 5000, '4500', 9500, '13 Feb 2023 09:43AM'),
//     createData(2, '0000006758', '5486958', 'Rosine', '340000', 4000, '300', 4300, '13 Feb 2023 09:43AM'),
//     createData(3, '0000006758', '4356566', 'Rosine', '56000', 6000, '1000', 7000, '13 Feb 2023 09:43AM'),

// ];

export default function MyTransactions({ token }) {

    // modal
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    const [open, setOpen] = React.useState(false)
    const handleOpen = () => setOpen(true)
    const handleClose = () => setOpen(false)

    // my transactions
    const [mytransactions, setMytransactions] = useState()
    const [loading, setLoading] = useState(true)

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };


    // get my transactions

    var formdata = new FormData();
    formdata.append("start", "1332407871");
    formdata.append("limit", "1679476671");

    var requestOptions = {
        method: 'POST',
        headers: {
            'Authorization': `Token ${token}`
        },
        body: formdata,
        redirect: 'follow'
    };


    React.useEffect(() => {
        fetch("http://127.0.0.1:8000/api/appgettransactions", requestOptions)
            .then(response => response.json())
            .then(result => {
                setMytransactions(JSON.parse(result));
                setLoading(false);
            })
            .catch(error => {
                console.log('error', error);
                setLoading(false);
            });
    }, []);




    // Define the request options including the HTTP method, headers, and data payload


    // var formdata = new FormData();
    // formdata.append("start", "1332407871");
    // formdata.append("limit", "1679476671");
    // var requestOptions = {
    //     method: 'post',
    //     url: 'http://127.0.0.1:8000/api/appgettransactions',
    //     headers: {
    //         Authorization: `Token ${token}` // Include an authorization token in the request headers
    //     },
    //     body: formdata,
    //     redirect: 'follow'

    // };

    // // Use the Axios library to send a POST request to the specified URL with the defined request options
    // axios(requestOptions)
    //     // When the response is received, access the data property which contains the response data as a JSON object
    //     .then(response => response.json)
    //     .then(result => console.log(JSON.parse(result)))
    //     // If an error occurs during the Axios request, log it to the console
    //     .catch(error => console.log('error', error));



    return (
        <Paper sx={{ width: '98%', margin: '0.5rem', overflowX: 'auto' }}>

            <div className='table-headers'>
                <div className="table-title">My Transactions</div>
                <Stack>
                    <Button className='app-buttons m-4 w-25 text-dark' variant="contained">Refresh</Button>
                </Stack>


                {loading ? <CircularProgress color="inherit" className='center p-5' /> : <span></span>}
            </div>

            {/* <div style={{height:400,width:'100%'}}>
              <DataGrid
               rows={rows}
               columns={columns}
               pageSize={5}
               rowsPerPageOptions={[5]}
               checkboxSelection
              />
            </div> */}



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
                        {loading ? <CircularProgress color="inherit" className='center p-5' /> : <span></span>}

                        {mytransactions &&
                            mytransactions.response
                                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                .map((mytransaction) => {
                                    return (

                                        <TableRow hover role="checkbox" tabIndex={-1} key={mytransaction.pk}>
                                            <TableCell>{mytransaction.transaction}</TableCell>
                                            <TableCell>{mytransaction.customerno}</TableCell>
                                            <TableCell>{mytransaction.customer}</TableCell>
                                            <TableCell>{mytransaction.amount} RWF</TableCell>
                                            <TableCell>{mytransaction.smscreditsopeningbalance} Cr.</TableCell>
                                            <TableCell>{mytransaction.smscredits} Cr.</TableCell>
                                            <TableCell>{mytransaction.smscreditsclosingbalance} Cr.</TableCell>
                                            <TableCell>{mytransaction.created}</TableCell>
                                            <TableCell>{mytransaction.beneficiary}</TableCell>


                                        </TableRow>
                                    )
                                })}





                    </TableBody>



                </Table>

            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={mytransactions && mytransactions.total}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />



        </Paper>
    );
}