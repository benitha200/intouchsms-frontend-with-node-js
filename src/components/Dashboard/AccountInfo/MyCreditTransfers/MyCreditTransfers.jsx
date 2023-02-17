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
import './MyCreditTransfers.css'
import { Form } from 'react-bootstrap';
import { DataGrid } from '@mui/x-data-grid' 

const columns = [

    {
        field: 'transferNo',
        headerName: 'Transfer No',
        // minWidth: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'customerNumber',
        headerName: 'Customer No',
        minWidth: 150,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'customerName',
        headerName: 'Customer Name',
        minWidth: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'phone',
        headerName: 'Phone',
        // minWidth: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'email',
        headerName: 'Email',
        width: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'amountTransfered',
        headerName: 'Amount Transfered',
        width: 130,
        align: 'center',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'created',
        headerName: 'Created',
        // minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
];

function createData(id,transferNo, customerNumber, customerName, phone, email, amountTransfered, created) {
    return { id,transferNo, customerNumber, customerName, phone, email, amountTransfered, created };
}

const rows = [
    createData(1,'0000006758', '235679875', 'ISCO', '0785467789', 'isco@isco.co.rw', 5000, 34567890),
    createData(2,'0000006758', '235679875', 'ISCO', '0785467789', 'isco@isco.co.rw', 5000, 34567890),
    createData(3,'0000006758', '235679875', 'ISCO', '0785467789', 'isco@isco.co.rw', 5000, 34567890),


];

export default function MyCreditTransfers() {
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    const [open, setOpen] = React.useState(false)
    const handleOpen = () => setOpen(true)
    const handleClose = () => setOpen(false)

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };


    return (
        <Paper sx={{ width: '98%', margin: '0.5rem', overflowX: 'auto' }}>
            <Stack spacing={2} direction="row" sx={{ margin: "1rem", paddingTop: "1rem" }}>
                <Button variant="outlined" onClick={setOpen} color='dark' className='credit-transfer-buttons'>Add My Credit Transfer</Button>
                <Modal
                    keepMounted
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="keep-mounted-modal-title"
                    aria-describedby="keep-mounted-modal-body"
                >
                    <Box className="credit-transfer-modal">
                        <div className='credit-transfer-modal-title'>
                            Add My Credit Transfer
                        </div>
                        <Stack>
                            <Box className='credit-transfer-modal-box'>
                                <div className='credit-transfer-modal-box-title'>
                                    <Typography className='credit-transfer-modal-box-title'>Credit Transfer Info</Typography>
                                </div>
                                <Form>
                                    <Stack direction="row" gap={2}>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Transfer No."
                                                defaultValue="(New)..."
                                                size='small'
                                                InputProps={{
                                                    readOnly: true,
                                                }}
                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-read-only-input"
                                                label="Description"
                                                defaultValue=""
                                                size='small'
                                                multiline
                                                rows={3}

                                            />
                                        </FormGroup>
                                    </Stack>

                                    <Stack direction="row" gap={2}>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Account Balance"
                                                size='small'
                                                InputProps={{
                                                    readOnly: true,
                                                }}
                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-read-only-input"
                                                label="Transfer Amount"
                                                defaultValue=""
                                                size='small'

                                            />
                                        </FormGroup>
                                    </Stack>



                                </Form>
                            </Box>

                            <Box className='credit-transfer-modal-box'>
                                <div className='credit-transfer-modal-box-title'>
                                    <Typography>Credit Transfer Info</Typography>
                                </div>
                                <Form>
                                    <Stack direction="row" gap={2}>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Customer No."
                                                size='small'
                                                InputProps={{
                                                    readOnly: true,
                                                }}
                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <Button className='credit-transfer-modal-form-buttons'>Select</Button>
                                        </FormGroup>
                                    </Stack>

                                    <Stack direction="row" gap={2}>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Customer Name"
                                                size='small'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-read-only-input"
                                                label="Phone"
                                                defaultValue=""
                                                size='small'

                                            />
                                        </FormGroup>
                                    </Stack>

                                    <Stack direction="row" gap={2}>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Email"
                                                size='small'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-read-only-input"
                                                label="Address"
                                                defaultValue=""
                                                size='small'

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Customer Balance"
                                                size='small'
                                                InputProps={{
                                                    readOnly: true,
                                                }}

                                            />
                                        </FormGroup>

                                    </Stack>

                                    <Stack direction="row" gap={2}>
                                        <Button className='credit-transfer-modal-buttons'>Submit</Button>
                                        <Button className='credit-transfer-modal-buttons'>Cancel</Button>
                                    </Stack>

                                </Form>
                            </Box>

                        </Stack>
                    </Box>


                </Modal>
                <Button className='credit-transfer-buttons' variant="contained" color='dark'>View & Modify</Button>
                <Button className='credit-transfer-buttons' variant="contained" color='dark'>Refresh</Button>
            </Stack>

            <div className='in-between'>
                 
            </div>

            <div style={{height:400,width:'100%'}}>
              <DataGrid
               rows={rows}
               columns={columns}
               pageSize={5}
               rowsPerPageOptions={[5]}
               checkboxSelection
              />
            </div>
            
            {/* <TableContainer sx={{ maxHeight: 440 }}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>

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
                                                    {column.format && typeof value === 'number'
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
            /> */}
        </Paper>
    );
}