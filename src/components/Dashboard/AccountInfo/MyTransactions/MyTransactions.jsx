// import * as React from 'react';
// import Paper from '@mui/material/Paper';
// import Table from '@mui/material/Table';
// import TableBody from '@mui/material/TableBody';
// import TableCell from '@mui/material/TableCell';
// import TableContainer from '@mui/material/TableContainer';
// import TableHead from '@mui/material/TableHead';
// import TablePagination from '@mui/material/TablePagination';
// import TableRow from '@mui/material/TableRow';
// import { Stack } from '@mui/system';
// import { Button } from 'reactstrap';
// import { Box, Modal, Typography } from '@mui/material';

// import './MyTransactions.css'
// const columns = [

//   {
//     id: 'transaction',
//     label: 'Transaction',
//     minWidth: 50,
//     align: 'left',
//     format: (value) => value.toLocaleString('en-US'),
//   },
//   {
//     id: 'customerNo',
//     label: 'Customer No',
//     minWidth: 100,
//     align: 'left',
//     format: (value) => value.toLocaleString('en-US'),
//   },
//   {
//     id: 'customerName',
//     label: 'Customer Name',
//     minWidth: 100,
//     align: 'left',
//     format: (value) => value.toLocaleString('en-US'),
//   },
//   {
//     id: 'smsQuantity',
//     label: 'SMS Quantity',
//     minWidth: 100,
//     align: 'left',
//     format: (value) => value.toLocaleString('en-US'),
//   },
//   {
//     id: 'openingBalance',
//     label: 'Opening Balance',
//     minWidth: 50,
//     align: 'left',
//     format: (value) => value.toLocaleString('en-US'),
//   },
//   {
//     id: 'amount',
//     label: 'Amount',
//     minWidth: 50,
//     align: 'right',
//     format: (value) => value.toLocaleString('en-US'),
//   },
//   {
//     id: 'closingBalance',
//     label: 'Closing Balance',
//     minWidth: 100,
//     align: 'right',
//     format: (value) => value.toLocaleString('en-US'),
//   },
//   {
//     id: 'created',
//     label: 'Created',
//     minWidth: 170,
//     align: 'right',
//     format: (value) => value.toLocaleString('en-US'),
//   },
// ];

// function createData(transaction, customerNo, customerName, smsQuantity,openingBalance,amount,closingBalance,created) {
//   return { transaction, customerNo, customerName, smsQuantity,openingBalance,amount,closingBalance,created};
// }

// const rows = [
//   createData('0000006758', '3489540', 'Rosine', '0785467789',5000,'4500',9500,34485990),
//   createData('0000006758', '5486958', 'Rosine', '0785467789',4000,'300',4300,34485990),
//   createData('0000006758', '4356566', 'Rosine', '0785467789',6000,'1000',7000,34485990),
 
// ];

// export default function MyTransactions() {
//   const [page, setPage] = React.useState(0);
//   const [rowsPerPage, setRowsPerPage] = React.useState(10);
//   const [open,setOpen] = React.useState(false)
//   const handleOpen=()=>setOpen(true)
//   const handleClose=()=>setOpen(false)

//   const handleChangePage = (event, newPage) => {
//     setPage(newPage);
//   };

//   const handleChangeRowsPerPage = (event) => {
//     setRowsPerPage(+event.target.value);
//     setPage(0);
//   };


//   return (
//     <Paper sx={{ width: '95%',margin:'1rem',overflowX:'auto' }}>
//       <Stack spacing={2} direction="row" sx={{margin:"1rem",paddingTop:"1rem"}}>
//         <Button variant="outlined" color='dark'>Add</Button>
//         {/* <Modal 
//         keepMounted
//         open={open}
//         onClose={handleClose}
//         aria-labelledby="keep-mounted-modal-title"
//         aria-describedby="keep-mounted-modal-body"
//         >
//             <Box>
//                 <Typography id="keep-mounted-modal-title" variant="h6" component="h2">
//                     Add Agent
//                 </Typography>
//                 <Typography id="keep-mounted-modal-description" sx={{ mt: 2 }}>
//             Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
//           </Typography>
//             </Box>
            

//         </Modal> */}
//         <Button variant="contained" color='dark'>View & Modify</Button>
//         <Button variant="contained" color='dark'>Refresh</Button>
//       </Stack>
//       <hr/>
//       <TableContainer sx={{ maxHeight: 440 }}>
//         <Table stickyHeader aria-label="sticky table">
//           <TableHead style={{fontWeight:'700px'}}>
       
//             <TableRow>
//               {columns.map((column) => (
//                 <TableCell
//                   key={column.id}
//                   align={column.align}
//                   style={{ top: 20, minWidth: column.minWidth,fontWeight:'bolder' }}
//                 >
//                   {column.label}
//                 </TableCell>
//               ))}
//             </TableRow>
//           </TableHead>
//           <TableBody>
//             {rows
//               .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
//               .map((row) => {
//                 return (
//                   <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
//                     {columns.map((column) => {
//                       const value = row[column.id];
//                       return (
//                         <TableCell key={column.id} align={column.align}>
//                           {column.format && typeof value === 'number'
//                             ? column.format(value)
//                             : value}
//                         </TableCell>
//                       );
//                     })}
//                   </TableRow>
//                 );
//               })}
//           </TableBody>
//         </Table>
//       </TableContainer>
//       <TablePagination
//         rowsPerPageOptions={[10, 25, 100]}
//         component="div"
//         count={rows.length}
//         rowsPerPage={rowsPerPage}
//         page={page}
//         onPageChange={handleChangePage}
//         onRowsPerPageChange={handleChangeRowsPerPage}
//       />
//     </Paper>
//   );
// }
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

const columns = [

    {
        field: 'transaction',
        headerName: 'Transaction No',
        // minWidth: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'customerNo',
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
        field: 'smsQuantity',
        headerName: 'SMS Quantity',
        // minWidth: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'openingBalance',
        headerName: 'Email',
        width: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'amount',
        headerName: 'Amount ',
        width: 130,
        align: 'center',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'closingBalance',
        headerName: 'ClosingBalance',
        minWidth: 120,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
];

function createData(id,transaction, customerNo, customerName, smsQuantity,openingBalance,amount,closingBalance,created) {
  return { id,transaction, customerNo, customerName, smsQuantity,openingBalance,amount,closingBalance,created};
}

const rows = [
  createData(1,'0000006758', '3489540', 'Rosine', '12000',5000,'4500',9500,'13 Feb 2023 09:43AM'),
  createData(2,'0000006758', '5486958', 'Rosine', '340000',4000,'300',4300,'13 Feb 2023 09:43AM'),
  createData(3,'0000006758', '4356566', 'Rosine', '56000',6000,'1000',7000,'13 Feb 2023 09:43AM'),
 
];

export default function MyTransactions() {
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
            <Stack>
            <Button className='my-transactions-buttons m-4' variant="contained" color='dark'>Refresh</Button>
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