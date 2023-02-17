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
import { Box, Typography } from '@mui/material';
import Modal from '@mui/material/Modal'
import './MyClients.css';
import { TextField } from '@mui/material';
import { Form, Label, FormGroup, Input, CloseButton } from 'reactstrap'


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

    // refresh

    const refresh = () =>window.location.reload(true)


    return (
        <Paper sx={{ width: '80%', margin: '1rem', overflowX: 'auto' }}>
            <Stack spacing={2} direction="row" sx={{ margin: "1rem", paddingTop: "1rem" }}>
                <Button variant="outlined" color='dark' onClick={handleOpen}>Add</Button>
                <Modal
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
                                                readOnly:true
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
                                                readOnly:true
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
                                                readOnly:true
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
                                                readOnly:true
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
                                                readOnly:true
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
                                                readOnly:true
                                            }}

                                        />
                                    </FormGroup>
                                </Stack>
                                


                                <Stack direction="row" gap={1}>
                                    <Button className='profile-buttons'>Save and Close</Button>
                                    <Button className='profile-buttons'>Save and New</Button>
                                    <Button className='profile-buttons'>Cancel</Button>
                                </Stack>

                            </Form>
                        </Stack>
                        </div>
                        
                    </Box>


                </Modal >

                <Button variant="contained" color='dark'>View & Modify</Button>
                <Button onClick={refresh} variant="contained" color='dark'>Refresh</Button>
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
            />
        </Paper >
    );
}